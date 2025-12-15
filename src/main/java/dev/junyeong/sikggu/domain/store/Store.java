package dev.junyeong.sikggu.domain.store;

import dev.junyeong.sikggu.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store {

  @Id
  @Column(name = "user_id")
  private Long id;

  @MapsId
  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private Double latitude;

  @Column(nullable = false)
  private Double longitude;

  public void update(String name, String phoneNumber, String address,
      Double latitude, Double longitude) {

    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;

  }

}