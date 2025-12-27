package dev.junyeong.sikggu.presentation.product.controller;

import dev.junyeong.sikggu.application.product.SaleProductService;
import dev.junyeong.sikggu.domain.product.ProductCategory;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductListResponse;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SaleProductPublicController {

  private final SaleProductService saleProductService;

  // 전체 - 주변 상품 조회
  @GetMapping("/products/nearby")
  public ResponseEntity<SaleProductListResponse> getNearbyUsers(
      @AuthenticationPrincipal User user) {
    SaleProductListResponse response = saleProductService.getNearbySaleProducts(user);
    return ResponseEntity.ok(response);
  }

  // 전체 - 가게에 등록된 전체 상품 조회
  @GetMapping("/stores/{storeId}/products")
  public ResponseEntity<SaleProductListResponse> getSaleItemsByStoreIdAndCategory(
      @PathVariable Long storeId,
      @RequestParam(required = false) ProductCategory category
  ) {
    SaleProductListResponse response = saleProductService.getSaleItemsByStoreIdAndCategory(storeId,
        category);
    return ResponseEntity.ok(response);
  }

  // 전체 - 가게 상품 상세 조회
  @GetMapping("/stores/{storeId}/products/{productId}")
  public ResponseEntity<SaleProductResponse> getSaleProductDetail(
      @PathVariable Long storeId,
      @PathVariable Long productId
  ) {
    SaleProductResponse response = saleProductService.getSaleProductDetail(storeId, productId);
    return ResponseEntity.ok(response);
  }
}
