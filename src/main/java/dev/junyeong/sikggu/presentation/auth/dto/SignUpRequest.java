package dev.junyeong.sikggu.presentation.auth.dto;

import dev.junyeong.sikggu.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpRequest(
    @NotBlank
    String email,
    @NotBlank
    String password,
    @NotBlank
    String nickname,
    @NotBlank
    String phoneNumber,
    @NotNull
    UserRole role
) {

  public Boolean isUser() {
    return role == UserRole.USER;
  }

  public Boolean isStore() {
    return role == UserRole.STORE;
  }
}
