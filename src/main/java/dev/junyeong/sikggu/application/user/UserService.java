package dev.junyeong.sikggu.application.user;

import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import dev.junyeong.sikggu.presentation.user.dto.UserResponse;
import dev.junyeong.sikggu.presentation.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse getInfo(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾지 못했습니다."));

    return UserResponse.from(user);
  }

  @Transactional
  public UserResponse updateInfo(Long userId, UserUpdateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("유저 정보를 찾지 못했습니다."));

    if (request.newPassword() != null) {
      String hashedPassword = passwordEncoder.encode(request.newPassword());
      user.updatePassword(hashedPassword); // User 엔티티에 이 메서드가 있어야 함
    }

    user.updateInfo(
        request.nickname(),
        request.phoneNumber(),
        request.latitude(),
        request.longitude()
    );

    // 💡 userRepository.save(user); 호출 생략 (더티 체킹 활용)
    // @Transactional이 종료될 때 변경사항이 자동으로 DB에 반영됩니다.

    return UserResponse.from(user);
  }

  @Transactional
  public void delete(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    userRepository.delete(user);
  }

}
