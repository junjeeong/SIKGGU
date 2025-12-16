package dev.junyeong.sikggu.presentation.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    String newPassword,

    @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해야 합니다.")
    String nickname,

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "올바른 전화번호 형식(010-xxxx-xxxx)을 사용해야 합니다.")
    String phoneNumber,

    Double latitude,
    Double longitude
) {

}
