package dev.junyeong.sikggu.presentation.store.dto;

import dev.junyeong.sikggu.domain.store.Store;
import java.util.List;
import java.util.stream.Collectors;

public record StoreListResponse(List<StoreResponse> list) {

  public static StoreListResponse from(List<Store> stores) {
    return new StoreListResponse(
        stores.stream()
            .map(StoreResponse::from)
            .collect(Collectors.toList())
    );
  }
}
