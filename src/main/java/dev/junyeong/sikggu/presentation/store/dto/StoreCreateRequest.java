package dev.junyeong.sikggu.presentation.store.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record StoreCreateRequest(
    @NotBlank(message = "가게 이름은 필수입니다.")
    String name,

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^0\\d{1,2}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 02-1234-5678)")
    String phoneNumber,

    @NotBlank(message = "주소는 필수입니다.")
    String address,

    @NotNull(message = "위도(Latitude)는 필수입니다.")
    @DecimalMin(value = "-90.0", message = "위도는 -90.0 이상이어야 합니다.") // 유효한 위도 범위
    @DecimalMax(value = "90.0", message = "위도는 90.0 이하여야 합니다.") // 유효한 위도 범위
    Double latitude,

    @NotNull(message = "경도(Longitude)는 필수입니다.")
    @DecimalMin(value = "-180.0", message = "경도는 -180.0 이상이어야 합니다.") // 유효한 경도 범위
    @DecimalMax(value = "180.0", message = "경도는 180.0 이하여야 합니다.") // 유효한 경도 범위
    Double longitude
) {

}