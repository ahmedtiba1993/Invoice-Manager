package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.CategoryRequest;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.CategoryMapper;
import com.tiba.invoice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
