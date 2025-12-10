package dev.junyeong.sikggu.domain;

public enum UserRole {

  USER,
  STORE;

  public String getName() {
    return "ROLE_" + this.name();
  }
}