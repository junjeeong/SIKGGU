package dev.junyeong.sikggu.presentation.store.dto;

import java.time.LocalDateTime;

public record SaleItemCreateRequest(String name, String description, Integer originalPrice,
                                    Integer salePrice, Integer quantity,
                                    LocalDateTime saleDeadline) {

}
