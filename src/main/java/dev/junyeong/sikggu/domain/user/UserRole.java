package dev.junyeong.sikggu.domain.user;

public enum UserRole {
  USER,
  STORE;

  public String getName() {
    return "ROLE_" + this.name();
  }
}