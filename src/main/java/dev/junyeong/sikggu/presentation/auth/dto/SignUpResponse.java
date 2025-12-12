package dev.junyeong.sikggu.presentation.auth.dto;

public record SignUpResponse(Long userId) {

  public static SignUpResponse from(Long userId) {
    return new SignUpResponse(userId);
  }
}
