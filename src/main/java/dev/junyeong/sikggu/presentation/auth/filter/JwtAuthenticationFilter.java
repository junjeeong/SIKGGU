package dev.junyeong.sikggu.presentation.auth.filter;

import dev.junyeong.sikggu.domain.auth.TokenService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
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

    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorizationHeader.substring(7);

    try {
      if (tokenService.validateToken(token)) {
        Long userId = tokenService.getUserIdFromToken(token);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("토큰의 사용자 ID를 찾을 수 없습니다."));

        // 💡 4. Authentication 객체 생성 (사용자 역할(Role) 반영)
        Collection<? extends SimpleGrantedAuthority> authorities = createAuthorities(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user, // Principal: User 객체
            null,
            authorities // 💡 User 객체의 역할을 기반으로 생성된 권한 목록
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("JWT 인증 실패: " + e.getMessage());
      // 인증 실패 시 명시적인 401 응답 처리
      // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    filterChain.doFilter(request, response);
  }

  // 💡 사용자의 역할을 SimpleGrantedAuthority 목록으로 변환하는 헬퍼 메서드
  private Collection<? extends SimpleGrantedAuthority> createAuthorities(User user) {
    // ⚠️ User 객체에서 역할 정보를 가져오는 부분은 실제 구현에 따라 달라질 수 있습니다.
    // 여기서는 user.getRole().name()이 "STORE_OWNER"나 "STUDENT"와 같은 문자열을 반환한다고 가정합니다.

    String roleName = user.getRole().name(); // 예: "STORE_OWNER"

    // Spring Security는 'ROLE_' 접두사를 포함한 문자열을 권한으로 처리하는 것이 일반적입니다.
    String grantedAuthorityName = "ROLE_" + roleName;

    return Collections.singletonList(new SimpleGrantedAuthority(grantedAuthorityName));
  }


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    String method = request.getMethod();

    return (method.equals("POST") && path.startsWith("/api/auth/sign-up")) ||
        (method.equals("POST") && path.startsWith("/api/auth/sign-in"));
  }

}