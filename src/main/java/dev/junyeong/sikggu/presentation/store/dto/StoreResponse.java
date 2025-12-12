package dev.junyeong.sikggu.presentation.store.dto;

import dev.junyeong.sikggu.domain.store.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreResponse(
    @NotNull Long id,
    @NotBlank String name,
    @NotBlank String phoneNumber,
    @NotBlank String address
) {

  /**
   * Store 엔티티를 받아 StoreResponse DTO로 변환하는 정적 팩토리 메서드
   *
   * @param store 변환할 Store 엔티티
   * @return StoreResponse DTO
   */
  public static StoreResponse from(Store store) {
    return new StoreResponse(
        store.getId(),
        store.getName(),
        store.getPhoneNumber(),
        store.getAddress()
    );
  }
}
