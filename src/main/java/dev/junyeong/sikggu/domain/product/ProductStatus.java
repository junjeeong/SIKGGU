package dev.junyeong.sikggu.domain.product;

public enum ProductStatus {

  AVAILABLE("판매 가능"),

  SOLD_OUT("재고 소진"),

  EXPIRED("마감됨"),

  STOPPED("판매 중단");

  private final String description;

  ProductStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}