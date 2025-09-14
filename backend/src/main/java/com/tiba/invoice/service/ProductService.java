package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.ProductRequest;
import com.tiba.invoice.entity.Category;
import com.tiba.invoice.entity.Product;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.ProductMapper;
import com.tiba.invoice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final ProductMapper productMapper;

  public Long addProduct(ProductRequest request) {

    List<String> errors = new ArrayList<>();

    if (productRepository.existsByCode(request.code())) {
      errors.add("PRODUCT_CODE_ALREADY_EXISTS");
    }

    if (productRepository.existsByName(request.name())) {
      errors.add("PRODUCT_NAME_ALREADY_EXISTS");
    }

    if (!errors.isEmpty()) {
      throw new DuplicateEntityException(errors);
    }

    Category category =
        categoryService
            .findCategoryById(request.categoryId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "CATEGORY_NOT_FOUND_WITH_ID_" + request.categoryId()));

    Product product = productMapper.toEntity(request);
    return productRepository.save(product).getId();
  }
}
