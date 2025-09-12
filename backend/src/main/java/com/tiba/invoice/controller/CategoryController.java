package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.CategoryRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> createCategory(
      @Valid @RequestBody CategoryRequest request) {

    Long newCategoryId = categoryService.addCategory(request);
    ApiResponse<Long> response =
        ApiResponse.success(newCategoryId, "CATEGORY_CREATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<Long>> updateCategory(
      @PathVariable Long id, @Valid @RequestBody CategoryRequest request) {

    Long updatedCategoryId = categoryService.updateCategory(id, request);
    ApiResponse<Long> response =
        ApiResponse.success(updatedCategoryId, "CATEGORY_UPDATED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
