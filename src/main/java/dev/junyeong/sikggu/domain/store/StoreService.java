package dev.junyeong.sikggu.domain.store;

import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;

public interface StoreService {

  StoreResponse create(Long id, String name, String phoneNumber, String address, Double latitude,
      Double longitude);

  StoreResponse getMyStore(Long userId);
}