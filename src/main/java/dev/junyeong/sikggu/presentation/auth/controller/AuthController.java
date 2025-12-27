package dev.junyeong.sikggu.presentation.auth.controller;

import dev.junyeong.sikggu.application.auth.AuthService;
import dev.junyeong.sikggu.presentation.auth.dto.AuthResponse;
import dev.junyeong.sikggu.presentation.auth.dto.SignInRequest;
import dev.junyeong.sikggu.presentation.auth.dto.SignUpResponse;
import dev.junyeong.sikggu.presentation.auth.dto.StoreSignUpRequest;
import dev.junyeong.sikggu.presentation.auth.dto.UserSignUpRequest;
import jakarta.validation.Valid;
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
  public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInRequest request) {
    AuthResponse token = authService.signIn(request);

    return ResponseEntity.ok(token);
  }

  @PostMapping("/sign-up/user")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody UserSignUpRequest request) {
    SignUpResponse response = authService.userSignUp(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/sign-up/store")
  public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody StoreSignUpRequest request) {
    SignUpResponse response = authService.storeSignUp(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
