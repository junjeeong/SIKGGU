package dev.junyeong.sikggu.presentation.auth.dto;

import dev.junyeong.sikggu.domain.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StoreSignUpRequest(
    // --- 계정(User) 관련 필드 ---
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d|(?=.*[@$!%*#?&]))[A-Za-z\\d@$!%*#?&]{8,20}$",
        message = "비밀번호는 영문, 숫자, 특수문자 중 2가지 이상을 포함해야 합니다.")
    String password,

    @NotBlank(message = "닉네임은 필수입니다.")
    String nickname,

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    String phoneNumber,

    @NotNull(message = "사용자 역할은 필수입니다.")
    UserRole role,

    // --- 상점(Store) 관련 필드 ---
    @NotBlank(message = "상점 이름은 필수입니다.")
    String storeName,

    @NotBlank(message = "상점 번호는 필수입니다.")
    String storeContactNumber,

    @NotBlank(message = "상점 주소는 필수입니다.")
    String address,

    Double latitude,

    Double longitude
) {

  public boolean isStore() {
    return role == UserRole.STORE;
  }
}