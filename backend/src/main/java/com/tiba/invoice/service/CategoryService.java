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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Transactional
  public Long addCategory(CategoryRequest request) {

    if (categoryRepository.existsByName(request.name())) {
      throw new DuplicateEntityException(Collections.singletonList("CATEGORY_NAME_ALREADY_EXISTS"));
    }

    return categoryRepository.save(categoryMapper.toCategory(request)).getId();
  }

  @Transactional
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

  @Transactional(readOnly = true)
  public PageResponseDto<CategoryResponse> getAllCategoriesPaginated(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
    Page<Category> categoryPage = categoryRepository.findAll(pageable);
    List<CategoryResponse> categoryList =
        categoryPage.stream().map(categoryMapper::toResponse).toList();
    return PageResponseDto.fromPage(categoryPage, categoryList);
  }

  @Transactional(readOnly = true)
  public List<CategoryResponse> getAllCategoriesList() {
    return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public Optional<Category> findCategoryById(Long id) {
    return categoryRepository.findById(id);
  }
}
