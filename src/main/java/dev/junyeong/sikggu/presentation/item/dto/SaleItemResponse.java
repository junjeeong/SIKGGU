package dev.junyeong.sikggu.presentation.item.dto;

import dev.junyeong.sikggu.domain.item.ItemStatus;
import dev.junyeong.sikggu.domain.item.SaleItem;
import java.time.LocalDateTime;

public record SaleItemResponse(Long id, Long storeId, String name, Integer salePrice,
                               Integer originalPrice, Integer quantity,
                               LocalDateTime saleDeadline, ItemStatus status,
                               LocalDateTime createdAt) {

  public static SaleItemResponse from(SaleItem saleItem) {
    // 엔티티 필드를 매핑하여 정규 생성자 호출
    return new SaleItemResponse(
        saleItem.getId(),
        saleItem.getStore().getId(), // Store 엔티티에서 ID 추출
        saleItem.getName(),
        saleItem.getSalePrice(),
        saleItem.getOriginalPrice(),
        saleItem.getStockQuantity(), // quantity 대신 stockQuantity로 가정
        saleItem.getSaleDeadline(),
        saleItem.getStatus(),
        saleItem.getCreatedAt()
    );
  }

}
