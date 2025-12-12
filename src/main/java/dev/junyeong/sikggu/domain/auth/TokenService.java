package dev.junyeong.sikggu.domain.auth;

public interface TokenService {

  String issueToken(Long userId);

  Boolean validateToken(String token);

  Long getUserIdFromToken(String token);
}
