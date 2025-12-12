package dev.junyeong.sikggu.presentation.store.controller;

import dev.junyeong.sikggu.domain.store.StoreService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.store.dto.StoreCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreRestController {

  private final StoreService storeService;

  @PostMapping
  public ResponseEntity<StoreResponse> createStore(@RequestBody StoreCreateRequest request,
      @AuthenticationPrincipal User user) {

    StoreResponse response = storeService.create(user.getId(), request.name(),
        request.phoneNumber(),
        request.address(),
        request.latitude(),
        request.longitude());

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/me")
  public ResponseEntity<StoreResponse> getMyStore(@AuthenticationPrincipal User user) {
    StoreResponse response = storeService.getMyStore(user.getId());

    return ResponseEntity.ok(response);
  }
}
