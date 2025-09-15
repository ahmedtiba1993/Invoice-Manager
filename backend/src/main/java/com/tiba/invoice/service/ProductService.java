package com.tiba.invoice.service;

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

  public PageResponseDto<ProductResponse> getAllProductsPaginated(int page, int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").ascending());
    Page<Product> productPage = productRepository.findAll(pageable);
    List<ProductResponse> productList =
        productPage.stream().map(productMapper::toResponse).toList();
    return PageResponseDto.fromPage(productPage, productList);
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(productMapper::toResponse).collect(Collectors.toList());
  }
}
