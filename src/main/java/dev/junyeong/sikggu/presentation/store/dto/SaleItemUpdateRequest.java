package dev.junyeong.sikggu.presentation.store.dto;

import dev.junyeong.sikggu.domain.item.ItemStatus;
import java.time.LocalDateTime;

public record SaleItemUpdateRequest(String name, String description, Integer originalPrice,
                                    Integer salePrice, Integer quantity,
                                    LocalDateTime saleDeadline, ItemStatus status) {

}
