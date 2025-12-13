package dev.junyeong.sikggu.presentation.store.dto;

import dev.junyeong.sikggu.domain.item.ItemStatus;
import java.time.LocalDateTime;

public record SaleItemResponse(Long id, Long storeId, String name, Integer salePrice,
                               Integer originalPrice, Integer quantity,
                               LocalDateTime saleDeadline, ItemStatus status,
                               LocalDateTime createdAt) {

}
