package dev.junyeong.sikggu.presentation.product.controller;

import dev.junyeong.sikggu.application.product.SaleProductService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductCreateRequest;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductListResponse;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductResponse;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/v1/products")
public class SaleProductAdminController {

  private final SaleProductService saleProductService;

  // 사장님 - 내가 등록한 상품 조회
  @GetMapping
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<SaleProductListResponse> getMySaleItems(
      @AuthenticationPrincipal User user) {

    SaleProductListResponse response = saleProductService.getMySaleProducts();

    return ResponseEntity.ok(response);
  }

  // 사장님 - 상품 등록
  @PostMapping
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<SaleProductResponse> createSaleItem(
      @AuthenticationPrincipal User user,
      @RequestBody SaleProductCreateRequest request) {

    SaleProductResponse response = saleProductService.registerSaleProduct(user.getId(), request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 사장님 - 상품 수정
  @PatchMapping("/{productId}")
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<SaleProductResponse> updateMySaleItem(
      @AuthenticationPrincipal User user,
      @PathVariable Long saleItemId,
      @Valid @RequestBody SaleProductUpdateRequest request) {

    SaleProductResponse response = saleProductService.updateSaleProduct(user.getId(), saleItemId,
        request);

    return ResponseEntity.ok(response);
  }

  // 사장님 - 상품 삭제
  @DeleteMapping("/{productId}")
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<Void> deleteMySaleItem(
      @AuthenticationPrincipal User user,
      @PathVariable Long productId) {

    saleProductService.deleteSaleProduct(user.getId(), productId);

    return ResponseEntity.noContent().build();
  }
}
