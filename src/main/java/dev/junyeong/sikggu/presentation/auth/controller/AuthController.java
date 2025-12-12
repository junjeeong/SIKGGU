package dev.junyeong.sikggu.presentation.auth.controller;

import dev.junyeong.sikggu.application.auth.AuthService;
import dev.junyeong.sikggu.presentation.auth.dto.AuthResponse;
import dev.junyeong.sikggu.presentation.auth.dto.SignInRequest;
import dev.junyeong.sikggu.presentation.auth.dto.SignUpRequest;
import dev.junyeong.sikggu.presentation.auth.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/sign-in")
  public ResponseEntity<AuthResponse> signIn(@RequestBody SignInRequest request) {
    AuthResponse token = authService.signIn(request);

    return ResponseEntity.ok(token);
  }

  @PostMapping("/sign-up")
  public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
    SignUpResponse response = authService.signUp(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
