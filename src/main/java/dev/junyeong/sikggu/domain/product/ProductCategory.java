package dev.junyeong.sikggu.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
  ALL("전체"),
  VEGETABLE("채소"),
  FRUIT("과일"),
  MEAT_EGG("정육·계란"),
  SEAFOOD("해산물"),
  GRAIN("곡류"),
  MEAL_KIT("밀키트"),
  DAIRY_CHEESE("유제품·치즈"),
  PROCESSED("가공식품"),
  FROZEN_REFRIGERATED("냉동·냉장");

  private final String description;
}