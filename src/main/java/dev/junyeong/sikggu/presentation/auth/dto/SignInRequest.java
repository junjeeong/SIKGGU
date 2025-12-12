package dev.junyeong.sikggu.presentation.auth.dto;

import dev.junyeong.sikggu.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(
    @NotBlank
    String email,
    @NotBlank
    String password,
    @NotNull
    UserRole role
) {

}
