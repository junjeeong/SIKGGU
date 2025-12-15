package dev.junyeong.sikggu.domain.store;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryCustom {

  @PersistenceContext
  private final EntityManager em;

  private static final double DEFAULT_RADIUS_METERS = 10000;

  @Override
  public List<Store> findNearbyStores(Double userLatitude, Double userLongitude) {

    String sql = "SELECT s.* FROM store s " +
        "WHERE ST_Distance_Sphere(POINT(s.longitude, s.latitude), POINT(?2, ?1)) <= ?3 " +
        "ORDER BY ST_Distance_Sphere(POINT(s.longitude, s.latitude), POINT(?2, ?1))";

    return em.createNativeQuery(sql, Store.class)
        .setParameter(1, userLatitude)
        .setParameter(2, userLongitude)
        .setParameter(3, DEFAULT_RADIUS_METERS)
        .getResultList();
  }
}