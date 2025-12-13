package dev.junyeong.sikggu.presentation.user.controller;

import dev.junyeong.sikggu.application.user.UserService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.user.dto.UserResponse;
import dev.junyeong.sikggu.presentation.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  // 사장님, 자취생 - 내 정보 조회
  @GetMapping("/me")
  public ResponseEntity<UserResponse> getInfo(@AuthenticationPrincipal User user) {
    UserResponse response = userService.getInfo(user.getId());

    return ResponseEntity.ok(response);
  }

  // 사장님, 자취생 - 내 정보 수정
  @PatchMapping("/me")
  public ResponseEntity<UserResponse> updateInfo(
      @AuthenticationPrincipal User user, @RequestBody UserUpdateRequest request,) {
    UserResponse response = userService.updateInfo(user.getId(), request);

    return ResponseEntity.ok(response);
  }

  // 사장님, 자취생 - 회원 탈퇴
  @DeleteMapping("/me")
  public ResponseEntity<UserResponse> deleteUser(
      @AuthenticationPrincipal User user) {
    userService.delete(user.getId());

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
