package dev.junyeong.sikggu.domain.product;

import dev.junyeong.sikggu.domain.store.Store;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne // Product는 하나의 Store에 속함 (N:1)
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  @Column(nullable = false)
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "original_price")
  private Integer originalPrice;

  @Column(name = "sale_price", nullable = false)
  private Integer salePrice;

  @Column(name = "stock_quantity", nullable = false)
  private Integer stockQuantity; // 동시성 제어 핵심 재고

  @Column(nullable = false)
  private LocalDateTime deadline; // 스케줄링 삭제 기준 마감시간

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductStatus status;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;


  public void decreaseStock(int quantity) {
    if (this.stockQuantity < quantity) {
      // @TODO : 커스텀 예외를 던지거나 재고 부족 처리 로직 구현
      throw new RuntimeException("재고가 부족합니다.");
    }
    this.stockQuantity -= quantity;

    if (this.stockQuantity == 0) {
      this.status = ProductStatus.SOLD_OUT;
    }
  }
}