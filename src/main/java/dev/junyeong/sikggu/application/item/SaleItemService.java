package dev.junyeong.sikggu.application.item;

import dev.junyeong.sikggu.domain.item.ItemStatus;
import dev.junyeong.sikggu.domain.saleitem.SaleItem;
import dev.junyeong.sikggu.domain.store.Store;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemCreateRequest;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemListResponse;
import dev.junyeong.sikggu.presentation.store.dto.SaleItemResponse;
import java.time.LocalDateTime;
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
  // StoreService에서 Store 엔티티를 넘겨주지 않고 ID만 넘겨주므로,
  // StoreRepository를 주입받아 Store 엔티티를 조회해야 할 수 있습니다.
  // 현재는 ID만 받아 처리하고 Store 엔티티는 가짜로 생성한다고 가정합니다.

  // --------------------------------------------------
  // 1. 상품 등록 (StoreService로부터 위임)
  // --------------------------------------------------

  @Transactional
  public SaleItemResponse registerSaleItem(Long storeId, SaleItemCreateRequest request) {
    // 1. Store 엔티티 조회 (실제로는 StoreService에서 Store를 찾아 넘겨주는 것이 더 효율적일 수 있음)
    // 현재는 ID만 받아 처리한다고 가정하고, Store는 임시로 생성합니다.
    Store dummyStore = Store.builder().id(storeId).build(); // 실제 구현 시 Repository 사용 필요

    // 2. SaleItem 엔티티 생성
    SaleItem newSaleItem = SaleItem.builder()
        .store(dummyStore) // 실제 Store 엔티티를 설정해야 합니다.
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
  // 2. 상점에서 진열하고 있는 전체 상품 조회 (StoreService로부터 위임)
  // --------------------------------------------------

  public SaleItemListResponse getSaleItemsByStoreId(Long storeId) {
    // Store ID로 상품 목록을 조회
    List<SaleItem> saleItems = saleItemRepository.findByStoreId(storeId);

    List<SaleItemResponse> responseList = saleItems.stream()
        .map(SaleItemResponse::from)
        .collect(Collectors.toList());

    return new SaleItemListResponse(responseList);
  }

  // --------------------------------------------------
  // 3. 상품 수정 (StoreService로부터 위임)
  // --------------------------------------------------

  @Transactional
  public SaleItemResponse updateSaleItem(Long storeId, Long saleItemId,
      SaleItemUpdateRequest request) {
    SaleItem saleItem = saleItemRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    // 🚨 중요: 소유권 검증 로직
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
  // 4. 상품 삭제 (StoreService로부터 위임)
  // --------------------------------------------------

  @Transactional
  public void deleteSaleItem(Long storeId, Long saleItemId) {
    SaleItem saleItem = saleItemRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    // 🚨 중요: 소유권 검증 로직
    if (!saleItem.getStore().getId().equals(storeId)) {
      throw new IllegalArgumentException("해당 상품을 삭제할 권한이 없습니다.");
    }

    // 삭제
    saleItemRepository.delete(saleItem);
  }

  // --------------------------------------------------
  // 5. 소비자용 상품 조회 (SaleItemController에서 사용될 예정)
  // --------------------------------------------------

  public SaleItemListResponse getNearbySaleItems(double latitude, double longitude) {
    // TODO: 좌표 기반으로 SaleItem을 조회하는 복잡한 쿼리 로직 구현 필요
    return SaleItemListResponse.empty();
  }

  public SaleItemResponse getSaleItemDetail(Long saleItemId) {
    SaleItem saleItem = saleItemRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    // 소비자에게 보여줄 수 없는 정보는 필터링하여 DTO로 변환
    return SaleItemResponse.from(saleItem);
  }
}