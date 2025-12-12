package dev.junyeong.sikggu.domain.order;

public enum OrderStatus {
  RESERVED("예약 완료"),
  PICKED_UP("수령 완료"),
  CANCELED("예약 취소");

  private final String description;

  OrderStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}