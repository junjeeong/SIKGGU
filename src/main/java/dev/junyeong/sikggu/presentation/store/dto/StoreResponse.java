package dev.junyeong.sikggu.presentation.store.dto;

import dev.junyeong.sikggu.domain.store.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreResponse(
    @NotNull Long id,
    @NotBlank String imageUrl,
    @NotBlank String storeName,
    @NotBlank String storeContactNumber,
    @NotBlank String description,
    @NotBlank String address
) {

  public static StoreResponse from(Store store) {
    return new StoreResponse(
        store.getId(),
        store.getImageUrl(),
        store.getStoreName(),
        store.getStoreContactNumber(),
        store.getDescription(),
        store.getAddress()
    );
  }
}
