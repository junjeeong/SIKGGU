package dev.junyeong.sikggu.presentation.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

  @GetMapping("/")
  public String healthCheck() {
    return "집에가자";
  }
}
