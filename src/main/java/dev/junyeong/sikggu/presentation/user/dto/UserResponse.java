package dev.junyeong.sikggu.presentation.user.dto;

import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRole;

public record UserResponse(
    String email,
    String nickname,
    String phoneNumber,
    UserRole role
) {

  public static UserResponse from(User user) {
    return new UserResponse(user.getEmail(), user.getNickname(), user.getPhoneNumber(),
        user.getRole());
  }
}
