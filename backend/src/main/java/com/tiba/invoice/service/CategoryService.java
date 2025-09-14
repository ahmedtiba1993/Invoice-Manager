package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.CategoryRequest;
import com.tiba.invoice.dto.response.CategoryResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.entity.Category;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.CategoryMapper;
import com.tiba.invoice.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public Long addCategory(CategoryRequest request) {

    List<String> errors = new ArrayList<>();
    if (categoryRepository.existsByName(request.name())) {
      errors.add("CATEGORY_NAME_ALREADY_EXISTS");
    }
    if (errors.size() > 0) {
      throw new DuplicateEntityException(errors);
    }

    return categoryRepository.save(categoryMapper.toCategory(request)).getId();
  }

  public Long updateCategory(Long id, CategoryRequest request) {

    Category existingCategory =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("CATEGORY_NOT_FOUND_WITH_ID_" + id));

    categoryRepository
        .findByName(request.name())
        .ifPresent(
            category -> {
              if (!category.getId().equals(id)) {
                throw new DuplicateEntityException(
                    Collections.singletonList("CATEGORY_NAME_ALREADY_EXISTS"));
              }
            });

    categoryMapper.updateEntity(existingCategory, request);

    return categoryRepository.save(existingCategory).getId();
  }

  public PageResponseDto<CategoryResponse> getAllCategoriesPaginated(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<Category> categoryPage = categoryRepository.findAll(pageable);
    List<CategoryResponse> categoryList =
        categoryPage.stream().map(categoryMapper::toResponse).toList();
    return PageResponseDto.fromPage(categoryPage, categoryList);
  }

  public List<CategoryResponse> getAllCategoriesList() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream().map(categoryMapper::toResponse).collect(Collectors.toList());
  }
}
