package dev.junyeong.sikggu.presentation.item.controller;

import dev.junyeong.sikggu.application.item.SaleItemService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemCreateRequest;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemListResponse;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemResponse;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemUpdateRequest;
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
@RequestMapping("/api/v1/sale-items")
public class SaleItemAdminController {

  private final SaleItemService saleItemService;

  // 사장님 - 내가 등록한 상품 조회
  @GetMapping
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<SaleItemListResponse> getMySaleItems(
      @AuthenticationPrincipal User user) {

    SaleItemListResponse response = saleItemService.getMySaleItems();

    return ResponseEntity.ok(response);
  }

  // 사장님 - 상품 등록
  @PostMapping
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<SaleItemResponse> createSaleItem(
      @AuthenticationPrincipal User user,
      @RequestBody SaleItemCreateRequest request) {

    SaleItemResponse response = saleItemService.registerSaleItem(user.getId(), request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 사장님 - 상품 수정
  @PatchMapping("/{saleItemId}")
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<SaleItemResponse> updateMySaleItem(
      @AuthenticationPrincipal User user,
      @PathVariable Long saleItemId,
      @Valid @RequestBody SaleItemUpdateRequest request) {

    SaleItemResponse response = saleItemService.updateSaleItem(user.getId(), saleItemId,
        request);

    return ResponseEntity.ok(response);
  }

  // 사장님 - 상품 삭제
  @DeleteMapping("/{saleItemId}")
  @PreAuthorize("hasRole('STORE')")
  public ResponseEntity<Void> deleteMySaleItem(
      @AuthenticationPrincipal User user,
      @PathVariable Long saleItemId) {

    saleItemService.deleteSaleItem(user.getId(), saleItemId);

    return ResponseEntity.noContent().build();
  }
}
