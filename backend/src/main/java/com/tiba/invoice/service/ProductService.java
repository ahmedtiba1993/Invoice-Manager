package com.tiba.invoice.service;

import com.tiba.invoice.dto.request.ProductFilterRequest;
import com.tiba.invoice.dto.request.ProductRequest;
import com.tiba.invoice.dto.response.PageResponseDto;
import com.tiba.invoice.dto.response.ProductResponse;
import com.tiba.invoice.entity.Category;
import com.tiba.invoice.entity.Product;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.mapper.ProductMapper;
import com.tiba.invoice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

  @Transactional(readOnly = true)
  public PageResponseDto<ProductResponse> getAllProductsPaginated(
      ProductFilterRequest filter, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").ascending());
    Page<Product> productPage =
        productRepository.filterProducts(
            filter.name(), filter.code(), filter.discountStatus(), filter.categoryId(), pageable);
    List<ProductResponse> productList =
        productPage.stream().map(productMapper::toResponse).toList();
    return PageResponseDto.fromPage(productPage, productList);
  }

  @Transactional(readOnly = true)
  public List<ProductResponse> getAllProducts() {
    return productRepository.findAll().stream().map(productMapper::toResponse).toList();
  }

  public void validateProductsExistence(List<Long> productIds) {
    List<Long> distinctProductIds = productIds.stream().distinct().toList();
    long foundProductsCount = productRepository.countByIdIn(distinctProductIds);
    if (foundProductsCount != distinctProductIds.size()) {
      throw new EntityNotFoundException("ONE_OR_MORE_PRODUCTS_NOT_FOUND_VERIFY_PROVIDED_IDS");
    }
  }
}
