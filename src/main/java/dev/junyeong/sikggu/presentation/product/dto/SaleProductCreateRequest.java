package dev.junyeong.sikggu.presentation.product.dto;

import java.time.LocalDateTime;

public record SaleProductCreateRequest(String name, String description, Integer originalPrice,
                                       Integer salePrice, Integer quantity,
                                       LocalDateTime saleDeadline) {

}
