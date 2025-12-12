package dev.junyeong.sikggu.presentation.auth.dto;

public record AuthResponse(String token) {

  public static AuthResponse from(String token) {
    return new AuthResponse(token);
  }
}
