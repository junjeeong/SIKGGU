package dev.junyeong.sikggu.domain.item;

import java.util.List;

public interface SaleItemRepositoryCustom {

  List<SaleItem> findNearbySaleItems(Double latitude, Double longitude);

}
