package dev.junyeong.sikggu.presentation.item.controller;

import dev.junyeong.sikggu.application.item.SaleItemService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale-items")
public class SaleItemController {

  private final SaleItemService saleItemService;

  // 자취생 - 주변 상품 조회
  @GetMapping("/nearby")
  public ResponseEntity<SaleItemListResponse> getNearbyUsers(@AuthenticationPrincipal User user) {
    SaleItemListResponse response = saleItemService.getNearbySaleItems(user.getLatitude(),
        user.getLatitude());
    return ResponseEntity.ok(response);
  }

  // 가게에 등록된 전체 상품 조회
  @GetMapping("/{storeId}")
  public ResponseEntity<SaleItemListResponse> getProductsByStore(@PathVariable Long storeId) {
    SaleItemListResponse response = saleItemService.getSaleItemsByStoreId(storeId);
    return ResponseEntity.ok(response);
  }

}
