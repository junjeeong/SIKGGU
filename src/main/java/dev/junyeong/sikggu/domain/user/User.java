package dev.junyeong.sikggu.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password; // 실제 저장 시에는 암호화(Hashing) 필요

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  @Column
  private Double latitude;

  @Column
  private Double longitude;


  public boolean isPasswordMatch(String rawPassword, PasswordEncoder passwordEncoder) {
    // DB에 저장된 암호화된 this.password와 입력된 rawPassword를 비교
    return passwordEncoder.matches(rawPassword, this.password);
  }

  public void updateInfo(String nickname, String phoneNumber, Double latitude,
      Double longitude) {

    if (!(nickname == null)) {
      this.nickname = nickname;
    }
    if (!(phoneNumber == null)) {
      this.phoneNumber = phoneNumber;
    }
    if (!(latitude == null)) {
      this.latitude = latitude;
    }
    if (!(longitude == null)) {
      this.longitude = longitude;
    }
  }

  public void updatePassword(String hashedPassword) {
    this.password = hashedPassword;
  }
}