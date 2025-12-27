package dev.junyeong.sikggu.domain.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Long>,
    SaleProductRepositoryCustom {

  List<SaleProduct> findByStoreIdAndStatus(Long storeId, ProductStatus status);
}
