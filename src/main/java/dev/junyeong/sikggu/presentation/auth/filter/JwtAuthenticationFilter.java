package dev.junyeong.sikggu.presentation.auth.filter;

import dev.junyeong.sikggu.domain.auth.TokenService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    // 1. HTTP 헤더에서 토큰 추출
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return; // 토큰이 없거나 형식이 잘못된 경우 인증 없이 다음 필터로 진행
    }

    String token = authorizationHeader.substring(7);

    try {
      // 2. 토큰 유효성 검증
      if (tokenService.validateToken(token)) {
        // 3. 토큰에서 사용자 ID 추출 및 User 조회
        Long userId = tokenService.getUserIdFromToken(token);

        // 💡 힌트: findById 대신 findByEmail 등을 사용할 수도 있습니다.
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("토큰의 사용자 ID를 찾을 수 없습니다."));

        // 4. Authentication 객체 생성 (권한은 임시로 USER 권한 하나만 부여)
        // 비밀번호(credentials)는 null로 설정합니다.
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user, // Principal: User 객체 자체를 담음 (이후 @AuthenticationPrincipal로 사용 가능)
            null,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // 권한 목록
        );

        // 5. SecurityContext에 Authentication 객체 설정 (인증 완료)
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      // 토큰 만료, 잘못된 서명 등 오류 발생 시 로그 기록 및 인증 실패 처리
      logger.error("JWT 인증 실패");
      // 💡 힌트: response.setStatus(401) 등으로 명시적인 응답을 보낼 수도 있습니다.
    }

    // 6. 다음 필터로 체인 진행
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    String method = request.getMethod();

    return (method.equals("POST") && path.startsWith("/api/auth/sign-up")) ||
        (method.equals("POST") && path.startsWith("/api/auth/sign-in"));
  }

}