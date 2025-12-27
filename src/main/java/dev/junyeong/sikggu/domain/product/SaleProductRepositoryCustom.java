package dev.junyeong.sikggu.domain.product;

import java.util.List;

public interface SaleProductRepositoryCustom {

  List<SaleProduct> findNearbySaleProducts(Double latitude, Double longitude);

}
