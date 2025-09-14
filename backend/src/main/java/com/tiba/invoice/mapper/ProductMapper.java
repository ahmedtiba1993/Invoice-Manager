package com.tiba.invoice.mapper;

import com.tiba.invoice.dto.request.ProductRequest;
import com.tiba.invoice.dto.response.ProductResponse;
import com.tiba.invoice.entity.Category;
import com.tiba.invoice.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

  public Product toEntity(ProductRequest request) {
    if (request == null) return null;

    Category category = new Category();
    category.setId(request.categoryId());

    return Product.builder()
        .name(request.name())
        .description(request.description())
        .code(request.code())
        .price(request.price())
        .discountStatus(request.discountStatus())
        .category(category)
        .build();
  }

  public ProductResponse toResponse(Product product) {
    if (product == null) return null;

    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .code(product.getCode())
        .price(product.getPrice())
        .discountStatus(product.getDiscountStatus())
        .categoryName(product.getCategory().getName())
        .build();
  }
}
