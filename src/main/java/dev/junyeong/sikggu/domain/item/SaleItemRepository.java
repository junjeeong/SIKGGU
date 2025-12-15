package dev.junyeong.sikggu.domain.item;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long>,
    SaleItemRepositoryCustom {

  List<SaleItem> findByStoreIdAndStatus(Long storeId, ItemStatus status);
}
