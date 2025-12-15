package dev.junyeong.sikggu.presentation.item.dto;

import dev.junyeong.sikggu.domain.item.SaleItem;
import java.util.List;
import java.util.stream.Collectors;

public record SaleItemListResponse(List<SaleItemResponse> items) {

  public static SaleItemListResponse from(List<SaleItem> saleItems) {
    // List<SaleItem> -> List<SaleItemResponse> 변환
    List<SaleItemResponse> responseList = saleItems.stream()
        .map(SaleItemResponse::from) // 🚨 SaleItemResponse::from 메서드가 필요합니다.
        .collect(Collectors.toList());

    // 정규 생성자를 사용하여 반환
    return new SaleItemListResponse(responseList);
  }

}
