package com.tiba.invoice.controller;

import com.tiba.invoice.dto.request.CategoryRequest;
import com.tiba.invoice.dto.response.ApiResponse;
import com.tiba.invoice.dto.response.CategoryResponse;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponseDto<CategoryResponse>>> getAllCategoriesPaginated(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {

    PageResponseDto<CategoryResponse> categoryPage =
        categoryService.getAllCategoriesPaginated(page, size);
    ApiResponse<PageResponseDto<CategoryResponse>> response =
        ApiResponse.success(categoryPage, "CATEGORIES_FETCHED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategoriesList() {
    List<CategoryResponse> categories = categoryService.getAllCategoriesList();
    ApiResponse<List<CategoryResponse>> response =
        ApiResponse.success(categories, "ALL_CATEGORIES_FETCHED_SUCCESSFULLY");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
