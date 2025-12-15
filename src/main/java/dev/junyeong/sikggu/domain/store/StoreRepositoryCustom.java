package dev.junyeong.sikggu.domain.store;

import java.util.List;

public interface StoreRepositoryCustom {

  List<Store> findNearbyStores(Double latitude, Double longitude);
}
