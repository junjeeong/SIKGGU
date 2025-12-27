package dev.junyeong.sikggu.presentation.product.dto;

import dev.junyeong.sikggu.domain.product.SaleProduct;
import dev.junyeong.sikggu.domain.store.Store;
import java.util.List;
import java.util.stream.Collectors;

public record SaleProductListResponse(StoreInfo store, List<SaleProductResponse> products) {

  public static SaleProductListResponse of(Store store, List<SaleProduct> saleProducts) {
    List<SaleProductResponse> responseList = saleProducts.stream()
        .map(SaleProductResponse::from)
        .collect(Collectors.toList());

    return new SaleProductListResponse(StoreInfo.from(store), responseList);
  }

  public static SaleProductListResponse fromEntities(List<SaleProduct> saleProducts) {
    List<SaleProductResponse> responseList = saleProducts.stream()
        .map(SaleProductResponse::from)
        .collect(Collectors.toList());

    return new SaleProductListResponse(null, responseList);
  }

  public static SaleProductListResponse fromDtos(List<SaleProductResponse> responseList) {
    return new SaleProductListResponse(null, responseList);
  }

  public record StoreInfo(Long id, String name, String address, Double latitude, Double longitude) {

    public static StoreInfo from(Store store) {
      if (store == null) {
        return null;
      }
      return new StoreInfo(
          store.getId(),
          store.getStoreName(),
          store.getAddress(),
          store.getLatitude(),
          store.getLongitude()
      );
    }
  }
}
