package dev.junyeong.sikggu.presentation.store.controller;

import dev.junyeong.sikggu.application.store.StoreService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemListResponse;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemResponse;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemUpdateRequest;
import dev.junyeong.sikggu.presentation.store.dto.StoreCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  // 가게 등록
  @PostMapping
  public ResponseEntity<StoreResponse> createStore(@RequestBody StoreCreateRequest request,
      @AuthenticationPrincipal User user) {

    StoreResponse response = storeService.createStore(user.getId(), request.name(),
        request.phoneNumber(), request.address(), request.latitude(), request.longitude());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 가게 정보 조회
  @GetMapping("/me")
  public ResponseEntity<StoreResponse> getStoreInfo(@AuthenticationPrincipal User user) {
    StoreResponse response = storeService.getStoreInfo(user.getId());

    return ResponseEntity.ok(response);
  }

  // 상품 등록
  @PostMapping("/sale-items")
  public ResponseEntity<SaleItemResponse> createSaleItem(
      @AuthenticationPrincipal User user,
      @RequestBody SaleItemCreateRequest request) {

    SaleItemResponse response = storeService.registerSaleItem(user.getId(), request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 상품 조회
  @GetMapping("/sale-items")
  public ResponseEntity<SaleItemListResponse> getMySaleItems(
      @AuthenticationPrincipal User user) {

    SaleItemListResponse response = storeService.getSaleItemsByStoreOwner(user.getId());

    return ResponseEntity.ok(response);
  }

  // 상품 수정
  @PatchMapping("/sale-items/{saleItemId}")
  public ResponseEntity<SaleItemResponse> updateMySaleItem(
      @AuthenticationPrincipal User user,
      @PathVariable Long saleItemId,
      @RequestBody SaleItemUpdateRequest request) {

    SaleItemResponse response = storeService.updateSaleItem(user.getId(), saleItemId,
        request);

    return ResponseEntity.ok(response);
  }

  // 상품 삭제
  @DeleteMapping("/sale-items/{saleItemId}")
  public ResponseEntity<Void> deleteMySaleItem(
      @AuthenticationPrincipal User user,
      @PathVariable Long saleItemId) {

    storeService.deleteSaleItem(user.getId(), saleItemId);

    return ResponseEntity.noContent().build();
  }

}