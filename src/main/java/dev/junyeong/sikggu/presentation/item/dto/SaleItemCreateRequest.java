package dev.junyeong.sikggu.presentation.item.dto;

import java.time.LocalDateTime;

public record SaleItemCreateRequest(String name, String description, Integer originalPrice,
                                    Integer salePrice, Integer quantity,
                                    LocalDateTime saleDeadline) {

}
