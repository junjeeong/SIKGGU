package dev.junyeong.sikggu.presentation.auth.dto;

import dev.junyeong.sikggu.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserSignUpRequest(
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.") // 이메일 형식 검증
    String email,

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.") // 길이 제한
    // 💡 비밀번호 복잡성 검증 (영문, 숫자, 특수문자 중 2가지 이상 포함)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d|(?=.*[@$!%*#?&]))[A-Za-z\\d@$!%*#?&]{8,20}$",
        message = "비밀번호는 영문, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.")
    String password,

    @NotBlank(message = "닉네임은 필수입니다.")
    String nickname,

    @NotBlank(message = "전화번호는 필수입니다.")
    // 💡 전화번호 형식 검증 (선택적)
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    String phoneNumber,

    @NotNull(message = "사용자 역할은 필수입니다.")
    UserRole role
) {

  public Boolean isUser() {
    return role == UserRole.USER;
  }

  public Boolean isStore() {
    return role == UserRole.STORE;
  }
}