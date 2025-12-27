package dev.junyeong.sikggu.presentation.product.dto;

import dev.junyeong.sikggu.domain.product.ProductStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record SaleProductUpdateRequest(
    @NotBlank(message = "상품 이름은 필수입니다.")
    @Size(max = 100, message = "상품 이름은 100자 이하여야 합니다.")
    String name,

    @Size(max = 500, message = "상품 설명은 500자 이하여야 합니다.")
    String description,

    @NotNull(message = "원래 가격은 필수입니다.")
    @Min(value = 0, message = "원래 가격은 0원 이상이어야 합니다.")
    Integer originalPrice,

    @NotNull(message = "할인 가격은 필수입니다.")
    @Min(value = 0, message = "할인 가격은 0원 이상이어야 합니다.")
    Integer salePrice,

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 0, message = "수량은 0개 이상이어야 합니다.")
    Integer quantity,

    @NotNull(message = "판매 마감 시각은 필수입니다.")
    // 마감 시각은 현재 시각보다 미래여야 함.
    @Future(message = "판매 마감 시각은 현재 시각보다 미래여야 합니다.")
    LocalDateTime saleDeadline,

    @NotNull(message = "상품 상태(status)는 필수입니다.")
    ProductStatus status
) {

}