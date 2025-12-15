package dev.junyeong.sikggu.application.auth;

import dev.junyeong.sikggu.domain.auth.TokenService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import dev.junyeong.sikggu.presentation.auth.dto.AuthResponse;
import dev.junyeong.sikggu.presentation.auth.dto.SignInRequest;
import dev.junyeong.sikggu.presentation.auth.dto.SignUpRequest;
import dev.junyeong.sikggu.presentation.auth.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;

  public AuthResponse signIn(SignInRequest request) {

    User user = userRepository.findByEmail(request.email()).orElseThrow(
        () -> new IllegalArgumentException(request.email() + "이메일을 가진 사용자를 찾지 못했습니다."));

    if (!user.isPasswordMatch(request.password(), passwordEncoder)) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    return AuthResponse.from(tokenService.issueToken(user.getId()));
  }


  public SignUpResponse signUp(SignUpRequest request) {
    userRepository.findByEmail(request.email()).ifPresent(user -> {
      throw new IllegalArgumentException("이미 존재하는 계정입니다.");
    });

    String hashedPassword = passwordEncoder.encode(request.password());

    User user = User.builder()
        .email(request.email())
        .password(hashedPassword)
        .nickname(request.nickname())
        .role(request.role())
        .phoneNumber(request.phoneNumber())
        .build();

    user = userRepository.save(user);

    return SignUpResponse.from(user.getId());

  }

}
