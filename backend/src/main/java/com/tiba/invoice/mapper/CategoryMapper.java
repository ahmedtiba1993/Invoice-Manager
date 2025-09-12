package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.request.CategoryRequest;
import com.tiba.invoice.dto.request.CategoryResponse;
import com.tiba.invoice.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

  public Category toCategory(CategoryRequest request) {
    if (request == null) return null;
    Category category = new Category();
    category.setName(request.name());
    category.setDescription(request.description());
    return category;
  }

  public CategoryResponse toResponse(Category category) {
    if (category == null) return null;
    CategoryResponse response = new CategoryResponse();
    response.setName(category.getName());
    response.setDescription(category.getDescription());
    return response;
  }

  public void updateEntity(Category existingCategory, CategoryRequest request) {
    if (request == null || existingCategory == null) {
      return;
    }
    existingCategory.setName(request.name());
    existingCategory.setDescription(request.description());
  }
}
