package dev.junyeong.sikggu.application.store;

import dev.junyeong.sikggu.application.item.SaleItemService;
import dev.junyeong.sikggu.domain.store.Store;
import dev.junyeong.sikggu.domain.store.StoreRepository;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.domain.user.UserRepository;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemListResponse;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemResponse;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemUpdateRequest;
import dev.junyeong.sikggu.presentation.store.dto.StoreListResponse;
import dev.junyeong.sikggu.presentation.store.dto.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

  private final StoreRepository storeRepository;
  private final UserRepository userRepository;
  private final SaleItemService saleItemService;

  // --------------------------------------------------
  // 1. Store 등록 및 조회
  // --------------------------------------------------

  @Transactional
  public StoreResponse createStore(Long userId, String name, String phoneNumber, String address,
      Double latitude, Double longitude) {

    // 사장님 한 명당 가게 하나만 등록 가능하다고 가정하고 Store ID를 User ID와 동일하게 사용
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

    Store savedStore = storeRepository.save(newStore);

    return StoreResponse.from(savedStore);
  }

  public StoreResponse getStoreInfo(Long userId) {
    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("가게를 찾을 수 없습니다. (UserID: " + userId + ")"));

    return StoreResponse.from(store);
  }

  public StoreListResponse getNearbyStores(Double latitude, Double longitude) {
    // TODO: 실제로는 StoreRepository에서 위도, 경도를 사용하여 주변 상점을 조회하는 로직 필요
    // 현재는 빈 목록 또는 전체 목록을 반환한다고 가정
    return StoreListResponse.empty();
  }

  // --------------------------------------------------
  // 2. SaleItem 관리 (StoreRestController에서 사용)
  // --------------------------------------------------

  @Transactional
  public SaleItemResponse registerSaleItem(Long userId, SaleItemCreateRequest request) {
    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            "가게 정보가 없어 상품 등록이 불가능합니다. (UserID: " + userId + ")"));

    return saleItemService.registerSaleItem(store.getId(), request);
  }

  // 3. getSaleItemsByStoreOwner() - 내가 등록한 상품 전체 조회
  public SaleItemListResponse getSaleItemsByStoreOwner(Long userId) {
    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            "가게를 찾을 수 없어 상품 조회가 불가능합니다. (UserID: " + userId + ")"));

    return saleItemService.getSaleItemsByStoreId(store.getId());
  }

  // 4. updateSaleItem() - 내가 등록한 상품 수정
  @Transactional
  public SaleItemResponse updateSaleItem(Long userId, Long saleItemId,
      SaleItemUpdateRequest request) {
    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            "가게 정보가 없어 상품 수정이 불가능합니다. (UserID: " + userId + ")"));

    return saleItemService.updateSaleItem(store.getId(), saleItemId, request);
  }

  // 5. deleteSaleItem() - 내가 등록한 상품 삭제
  @Transactional
  public void deleteSaleItem(Long userId, Long saleItemId) {
    Store store = storeRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            "가게 정보가 없어 상품 삭제가 불가능합니다. (UserID: " + userId + ")"));

    saleItemService.deleteSaleItem(store.getId(), saleItemId);
  }

  // --------------------------------------------------
  // 3. 추가적인 Store 관리 기능 (StoreRestController용)
  // --------------------------------------------------

  @Transactional
  public void deleteStore(Long userId, Long storeId) {
    if (!userId.equals(storeId)) {
      throw new IllegalArgumentException("해당 가게의 소유주가 아닙니다.");
    }

    storeRepository.deleteById(storeId);
  }
}