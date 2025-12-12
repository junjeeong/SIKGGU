package dev.junyeong.sikggu.presentation.store.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreCreateRequest(
    @NotBlank String name,
    @NotBlank String phoneNumber,
    @NotBlank String address,
    @NotNull Double latitude,
    @NotNull Double longitude
) {

}
