package dev.junyeong.sikggu.domain;

public enum ProductStatus {

  /**
   * 판매 가능한 상태 (재고가 남아있고, 마감 시간이 지나지 않음)
   */
  AVAILABLE("판매 가능"),

  /**
   * 재고가 소진되어 품절된 상태 (마감 시간과 무관하게 재고 0)
   */
  SOLD_OUT("재고 소진"),

  /**
   * 마감 시간이 지나 판매가 중단된 상태 (스케줄러에 의해 변경됨)
   */
  EXPIRED("마감됨"),

  /**
   * 사장님에 의해 수동으로 판매 중단된 상태 (필요 시 추가)
   */
  STOPPED("판매 중단");

  private final String description;

  ProductStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}