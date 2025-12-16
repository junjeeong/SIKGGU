package dev.junyeong.sikggu.application.item;

import dev.junyeong.sikggu.domain.item.ItemStatus;
import dev.junyeong.sikggu.domain.item.SaleItem;
import dev.junyeong.sikggu.domain.item.SaleItemRepository;
import dev.junyeong.sikggu.domain.store.Store;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemCreateRequest;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemListResponse;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemResponse;
import dev.junyeong.sikggu.presentation.item.dto.SaleItemUpdateRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaleItemService {

  private final SaleItemRepository saleItemRepository;

  // --------------------------------------------------
  // 1. 상품 등록
  // --------------------------------------------------

  @Transactional
  public SaleItemResponse registerSaleItem(Long storeId, SaleItemCreateRequest request) {
    Store dummyStore = Store.builder().id(storeId).build();

    // 2. SaleItem 엔티티 생성
    SaleItem newSaleItem = SaleItem.builder()
        .store(dummyStore)
        .name(request.name())
        .description(request.description())
        .originalPrice(request.originalPrice())
        .salePrice(request.salePrice())
        .stockQuantity(request.quantity())
        .saleDeadline(request.saleDeadline())
        .status(ItemStatus.AVAILABLE)
        .createdAt(LocalDateTime.now())
        .build();

    SaleItem savedItem = saleItemRepository.save(newSaleItem);
    return SaleItemResponse.from(savedItem);
  }

  // --------------------------------------------------
  // 2. 사장님 전용 상품 조회
  // --------------------------------------------------

  public SaleItemListResponse getMySaleItems() {
    List<SaleItem> saleItems = saleItemRepository.findAll();

    List<SaleItemResponse> responseList = saleItems.stream()
        .map(SaleItemResponse::from)
        .collect(Collectors.toList());

    return new SaleItemListResponse(responseList);
  }

  // --------------------------------------------------
  // 3. 사장님 전용 상품 수정
  // --------------------------------------------------

  @Transactional
  public SaleItemResponse updateSaleItem(Long storeId, Long saleItemId,
      SaleItemUpdateRequest request) {
    SaleItem saleItem = saleItemRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    if (!saleItem.getStore().getId().equals(storeId)) {
      throw new IllegalArgumentException("해당 상품을 수정할 권한이 없습니다.");
    }

    // TODO: SaleItem 엔티티에 수정 로직을 추가하고 호출 (예: saleItem.update(...))
    // 현재는 DTO에 setter를 사용하는 대신 빌더 패턴이나 update 메서드를 사용한다고 가정
    // saleItem.update(request);

    // 수정 후 응답 반환 (트랜잭션에 의해 변경 사항 자동 반영)
    return SaleItemResponse.from(saleItem);
  }

  // --------------------------------------------------
  // 4. 사장님 전용 - 상품 삭제
  // --------------------------------------------------

  @Transactional
  public void deleteSaleItem(Long storeId, Long saleItemId) {
    SaleItem saleItem = saleItemRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    if (!saleItem.getStore().getId().equals(storeId)) {
      throw new IllegalArgumentException("해당 상품을 삭제할 권한이 없습니다.");
    }

    saleItemRepository.delete(saleItem);
  }

  // --------------------------------------------------
  // 5. 소비자용 상품 조회 (SaleItemController에서 사용될 예정)
  // --------------------------------------------------

  public SaleItemListResponse getNearbySaleItems(User user) {
    // TODO: 좌표 기반으로 SaleItem을 조회하는 복잡한 쿼리 로직 구현 필요
    List<SaleItem> saleItems = saleItemRepository.findNearbySaleItems(user.getLatitude(),
        user.getLongitude());

    return SaleItemListResponse.from(saleItems);
  }

  public SaleItemResponse getSaleItemDetail(Long saleItemId) {
    SaleItem saleItem = saleItemRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    return SaleItemResponse.from(saleItem);
  }

  public SaleItemListResponse getSaleItemsByStoreId(Long storeId) {
    List<SaleItem> saleItems = saleItemRepository.findByStoreIdAndStatus(storeId,
        ItemStatus.AVAILABLE);

    if (saleItems.isEmpty()) {
      return new SaleItemListResponse(Collections.emptyList());
    }

    List<SaleItemResponse> responseList = saleItems.stream()
        .map(SaleItemResponse::from)
        .collect(Collectors.toList());

    return new SaleItemListResponse(responseList);
  }
}