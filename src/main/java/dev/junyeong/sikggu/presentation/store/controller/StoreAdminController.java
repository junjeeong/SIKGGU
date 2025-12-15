package dev.junyeong.sikggu.presentation.store.controller;

import dev.junyeong.sikggu.application.store.StoreService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.store.dto.StoreCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import dev.junyeong.sikggu.presentation.store.dto.StoreUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreAdminController {

  private final StoreService storeService;

  // 사장님 - 가게 등록
  @PostMapping
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<StoreResponse> createStore(@AuthenticationPrincipal User user,
      @Valid @RequestBody StoreCreateRequest request) {

    StoreResponse response = storeService.createStore(user.getId(), request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 사장님 - 내 가게 정보 수정
  @PatchMapping("/{storeId}")
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<StoreResponse> updateStore(@AuthenticationPrincipal User user,
      @PathVariable Long storeId, @Valid @RequestBody StoreUpdateRequest request) {
    StoreResponse response = storeService.updateStoreInfo(user.getId(), storeId, request);

    return ResponseEntity.ok(response);
  }

  // 사장님 - 내가 등록한 가게 정보 조회
  @GetMapping("/me")
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<StoreResponse> getStoreInfo(@AuthenticationPrincipal User user) {
    StoreResponse response = storeService.getStoreInfo(user.getId());

    return ResponseEntity.ok(response);
  }

}