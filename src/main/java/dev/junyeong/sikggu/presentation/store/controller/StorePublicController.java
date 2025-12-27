package dev.junyeong.sikggu.presentation.store.controller;

import dev.junyeong.sikggu.application.store.StoreService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.store.dto.StoreListResponse;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StorePublicController {

  private final StoreService storeService;

  // 전체 - 모든 마트 조회
  @GetMapping
  public ResponseEntity<StoreListResponse> getAllStores() {

    StoreListResponse response = storeService.getAllStores();

    return ResponseEntity.ok(response);
  }

  // 전체 - 주변 마트 조회
  @GetMapping("/nearby")
  public ResponseEntity<StoreListResponse> getNearbyStores(
      @AuthenticationPrincipal User user) {

    StoreListResponse response = storeService.getNearbyStores(user.getLatitude(),
        user.getLongitude());

    return ResponseEntity.ok(response);
  }

  // 전체 - 특정 마트 상세 조회
  @GetMapping("/{storeId}")
  public ResponseEntity<StoreResponse> getStoreDetail(@PathVariable Long storeId) {
    StoreResponse response = storeService.getStoreById(storeId);
    return ResponseEntity.ok(response);
  }

}
