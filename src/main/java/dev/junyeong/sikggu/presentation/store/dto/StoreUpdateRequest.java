package dev.junyeong.sikggu.presentation.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreUpdateRequest(
    @NotBlank(message = "가게 이름은 필수입니다.")
    String name,

    String imageUrl,
    
    @NotBlank(message = "전화번호는 필수입니다.")
    String phoneNumber,

    @NotBlank(message = "주소는 필수입니다.")
    String address,

    @NotNull(message = "위도는 필수입니다.")
    Double latitude,

    @NotNull(message = "경도는 필수입니다.")
    Double longitude
) {

}