package dev.junyeong.sikggu.application.store;

import dev.junyeong.sikggu.domain.store.Store;
import dev.junyeong.sikggu.domain.store.StoreRepository;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import dev.junyeong.sikggu.presentation.store.dto.StoreCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.StoreListResponse;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import dev.junyeong.sikggu.presentation.store.dto.StoreUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

  private final StoreRepository storeRepository;
  private final UserRepository userRepository;

  @Transactional
  public StoreResponse createStore(Long userId, StoreCreateRequest request) {

    storeRepository.findById(userId).ifPresent(store -> {
      throw new IllegalStateException("이미 가게를 등록했습니다. 중복 등록할 수 없습니다.");
    });

    User user = userRepository.findById(userId)
        .orElseThrow(
            () -> new IllegalArgumentException("사용자(ID: " + userId + ")를 찾을 수 없습니다."));

    Store newStore = Store.builder()
        .user(user)
        .storeName(request.name())
        .storeContactNumber(request.phoneNumber())
        .address(request.address())
        .latitude(request.latitude())
        .longitude(request.longitude())
        .build();

    Store savedStore = storeRepository.save(newStore);

    return StoreResponse.from(savedStore);
  }

  @Transactional(readOnly = true)
  public StoreListResponse getAllStores() {
    List<Store> stores = storeRepository.findAll().stream()
        .toList();

    return StoreListResponse.from(stores);
  }

  public StoreResponse getStoreById(Long storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(
            () -> new IllegalArgumentException("해당 ID의 가게를 찾을 수 없습니다. (StoreID: " + storeId + ")"));

    return StoreResponse.from(store);
  }
  
  public StoreResponse getStoreInfo(Long userId) {
    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다. (UserID: " + userId + ")"));

    return StoreResponse.from(store);
  }

  public StoreListResponse getNearbyStores(Double latitude, Double longitude) {
    List<Store> nearbyStores = storeRepository.findNearbyStores(latitude, longitude);

    return StoreListResponse.from(nearbyStores);
  }

  @Transactional
  public StoreResponse updateStoreInfo(Long userId, Long storeId, StoreUpdateRequest request) {
    if (!userId.equals(storeId)) {
      throw new IllegalArgumentException("해당 가게의 소유주가 아닙니다.");
    }

    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다. (UserID: " + userId + ")"));

    store.update(request.name(), request.phoneNumber(), request.address(), request.imageUrl(),
        request.latitude(),
        request.longitude());

    return StoreResponse.from(store);
  }

  @Transactional
  public void deleteStore(Long userId, Long storeId) {
    if (!userId.equals(storeId)) {
      throw new IllegalArgumentException("해당 가게의 소유주가 아닙니다.");
    }

    storeRepository.deleteById(storeId);
  }
}