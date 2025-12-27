package dev.junyeong.sikggu.domain.product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// 🚨 주의: SaleItemRepositoryCustom 인터페이스를 구현해야 합니다.
@Repository
@RequiredArgsConstructor
public class SaleProductRepositoryImpl implements SaleProductRepositoryCustom {

  @PersistenceContext
  private final EntityManager em;

  // 상품 조회 반경은 10km로 가정
  private static final double DEFAULT_RADIUS_METERS = 10000;

  @Override
  public List<SaleProduct> findNearbySaleProducts(Double userLatitude, Double userLongitude) {

    // 🚨 쿼리 설명:
    // 1. SaleItem이 속한 Store의 위/경도를 사용
    // 2. ST_Distance_Sphere를 이용해 2km 반경 내인지 필터링
    // 3. sale_deadline이 현재 시간(NOW())보다 크고 (만료되지 않음)
    // 4. sale_status가 AVAILABLE인 상품만 조회하도록 조건을 추가해야 함 (엔티티 정의 가정)

    String sql = "SELECT si.* FROM sale_item si " +
        "JOIN store s ON si.store_id = s.user_id " + // Store ID와 User ID가 동일하다는 가정
        "WHERE ST_Distance_Sphere(POINT(s.longitude, s.latitude), POINT(?2, ?1)) <= ?3 " +
        "AND si.sale_deadline > NOW() " + // 마감 기한이 지나지 않은 상품
        "AND si.sale_status = 'AVAILABLE' " + // 판매 가능한 상태의 상품
        "ORDER BY ST_Distance_Sphere(POINT(s.longitude, s.latitude), POINT(?2, ?1))";

    return em.createNativeQuery(sql, SaleProduct.class)
        // 위치 기반 바인딩: POINT(?2, ?1) => POINT(userLon, userLat)
        .setParameter(1, userLatitude)
        .setParameter(2, userLongitude)
        .setParameter(3, DEFAULT_RADIUS_METERS)
        .getResultList();
  }
}