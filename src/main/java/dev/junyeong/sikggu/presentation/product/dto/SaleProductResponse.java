package dev.junyeong.sikggu.presentation.product.dto;

import dev.junyeong.sikggu.domain.product.ProductCategory;
import dev.junyeong.sikggu.domain.product.ProductStatus;
import dev.junyeong.sikggu.domain.product.SaleProduct;
import java.time.LocalDateTime;

public record SaleProductResponse(
    Long id,
    String store,
    String name,
    ProductCategory productCategory,
    String imageUrl,
    String description,
    Integer originalPrice,
    Integer salePrice,
    Integer quantity,
    LocalDateTime saleDeadline,
    LocalDateTime createdAt,
    ProductStatus status
) {

  public static SaleProductResponse from(SaleProduct saleProduct) {
    return new SaleProductResponse(
        saleProduct.getId(),
        saleProduct.getStore().getStoreName(),
        saleProduct.getName(),
        saleProduct.getCategory(),
        saleProduct.getImageUrl(),
        saleProduct.getDescription(),
        saleProduct.getOriginalPrice(),
        saleProduct.getSalePrice(),
        saleProduct.getStockQuantity(),
        saleProduct.getSaleDeadline(),
        saleProduct.getCreatedAt(),
        saleProduct.getStatus()
    );
  }

}
