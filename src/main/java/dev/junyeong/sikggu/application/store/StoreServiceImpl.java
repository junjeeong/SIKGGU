package dev.junyeong.sikggu.application.store;

import dev.junyeong.sikggu.domain.store.Store;
import dev.junyeong.sikggu.domain.store.StoreRepository;
import dev.junyeong.sikggu.domain.store.StoreService;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

  private final StoreRepository storeRepository;
  private final UserRepository userRepository;

  @Override
  public StoreResponse create(Long userId, String name, String phoneNumber, String address,
      Double latitude, Double longitude) {

    storeRepository.findById(userId).ifPresent(store -> {
      throw new IllegalArgumentException("이미 존재하는 가게입니다.");
    });

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자(ID: " + userId + ")를 찾을 수 없습니다."));

    Store newStore = Store.builder()
        .id(userId)
        .user(user)
        .name(name)
        .phoneNumber(phoneNumber)
        .address(address)
        .latitude(latitude)
        .longitude(longitude)
        .build();

    newStore = storeRepository.save(newStore);

    return StoreResponse.from(newStore);
  }

  @Override
  public StoreResponse getMyStore(Long userId) {
    return null;
  }
}
