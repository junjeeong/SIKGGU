package dev.junyeong.sikggu.domain.item; // 패키지 이름도 변경 필요

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
@Table(name = "sale_item") // 테이블 이름도 변경
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SaleItem { // 엔티티 이름 변경

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne // SaleItem은 하나의 Store에 속함 (N:1)
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

  @Column(name = "sale_deadline", nullable = false) // 필드 이름 변경
  private LocalDateTime saleDeadline; // 스케줄링 삭제 기준 마감시간

  @Enumerated(EnumType.STRING)
  @Column(name = "sale_status", nullable = false) // 필드 이름 변경
  private ItemStatus status;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;


  public void decreaseStock(int quantity) {
    if (this.stockQuantity < quantity) {
      // @TODO : 커스텀 예외를 던지거나 재고 부족 처리 로직 구현
      throw new RuntimeException("재고가 부족합니다.");
    }
    this.stockQuantity -= quantity;

    if (this.stockQuantity == 0) {
      // ProductStatus 대신 SaleStatus.SOLD_OUT을 사용해야 합니다.
      this.status = ItemStatus.SOLD_OUT;
    }
  }
}