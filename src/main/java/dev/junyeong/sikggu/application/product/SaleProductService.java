package dev.junyeong.sikggu.application.product;

import dev.junyeong.sikggu.domain.product.ProductCategory;
import dev.junyeong.sikggu.domain.product.ProductStatus;
import dev.junyeong.sikggu.domain.product.SaleProduct;
import dev.junyeong.sikggu.domain.product.SaleProductRepository;
import dev.junyeong.sikggu.domain.store.Store;
import dev.junyeong.sikggu.domain.store.StoreRepository;
import dev.junyeong.sikggu.domain.user.User;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductCreateRequest;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductListResponse;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductResponse;
import dev.junyeong.sikggu.presentation.product.dto.SaleProductUpdateRequest;
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
public class SaleProductService {

  private final SaleProductRepository saleProductRepository;
  private final StoreRepository storeRepository;

  @Transactional
  public SaleProductResponse registerSaleProduct(Long storeId, SaleProductCreateRequest request) {
    Store dummyStore = Store.builder().id(storeId).build();

    SaleProduct newSaleProduct = SaleProduct.builder()
        .store(dummyStore)
        .name(request.name())
        .description(request.description())
        .originalPrice(request.originalPrice())
        .salePrice(request.salePrice())
        .stockQuantity(request.quantity())
        .saleDeadline(request.saleDeadline())
        .status(ProductStatus.AVAILABLE)
        .createdAt(LocalDateTime.now())
        .build();

    SaleProduct savedItem = saleProductRepository.save(newSaleProduct);
    return SaleProductResponse.from(savedItem);
  }

  // --------------------------------------------------
  // 2. 사장님 전용 상품 조회
  // --------------------------------------------------

  public SaleProductListResponse getMySaleProducts() {
    List<SaleProduct> saleProducts = saleProductRepository.findAll();

    List<SaleProductResponse> responseList = saleProducts.stream()
        .map(SaleProductResponse::from)
        .collect(Collectors.toList());

    return SaleProductListResponse.fromDtos(responseList);
  }

  // --------------------------------------------------
  // 3. 사장님 전용 상품 수정
  // --------------------------------------------------

  @Transactional
  public SaleProductResponse updateSaleProduct(Long storeId, Long saleItemId,
      SaleProductUpdateRequest request) {
    SaleProduct saleProduct = saleProductRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    if (!saleProduct.getStore().getId().equals(storeId)) {
      throw new IllegalArgumentException("해당 상품을 수정할 권한이 없습니다.");
    }

    // TODO: SaleProduct 엔티티에 수정 로직을 추가하고 호출 (예: saleItem.update(...))
    // 현재는 DTO에 setter를 사용하는 대신 빌더 패턴이나 update 메서드를 사용한다고 가정
    // saleItem.update(request);

    // 수정 후 응답 반환 (트랜잭션에 의해 변경 사항 자동 반영)
    return SaleProductResponse.from(saleProduct);
  }

  // --------------------------------------------------
  // 4. 사장님 전용 - 상품 삭제
  // --------------------------------------------------

  @Transactional
  public void deleteSaleProduct(Long storeId, Long saleItemId) {
    SaleProduct saleProduct = saleProductRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    if (!saleProduct.getStore().getId().equals(storeId)) {
      throw new IllegalArgumentException("해당 상품을 삭제할 권한이 없습니다.");
    }

    saleProductRepository.delete(saleProduct);
  }

  // --------------------------------------------------
  // 5. 소비자용 상품 조회 (SaleProductController에서 사용될 예정)
  // --------------------------------------------------

  public SaleProductListResponse getNearbySaleProducts(User user) {
    // TODO: 좌표 기반으로 SaleProduct을 조회하는 복잡한 쿼리 로직 구현 필요
    List<SaleProduct> saleProducts = saleProductRepository.findNearbySaleProducts(
        user.getLatitude(),
        user.getLongitude());

    return SaleProductListResponse.fromEntities(saleProducts);
  }

  public SaleProductResponse getSaleProductDetail(Long saleItemId) {
    SaleProduct saleProduct = saleProductRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    return SaleProductResponse.from(saleProduct);
  }

  public SaleProductResponse getSaleProductDetail(Long storeId, Long saleItemId) {
    SaleProduct saleProduct = saleProductRepository.findById(saleItemId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. (ID: " + saleItemId + ")"));

    if (!saleProduct.getStore().getId().equals(storeId)) {
      throw new IllegalArgumentException("해당 상점의 상품이 아닙니다.");
    }

    return SaleProductResponse.from(saleProduct);
  }

  public SaleProductListResponse getSaleItemsByStoreIdAndCategory(Long storeId,
      ProductCategory category) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(() -> new IllegalArgumentException("상점을 찾을 수 없습니다. (ID: " + storeId + ")"));

    List<SaleProduct> saleProducts = saleProductRepository.findByStoreIdAndStatus(storeId,
        ProductStatus.AVAILABLE);

    if (saleProducts.isEmpty()) {
      return SaleProductListResponse.of(store, Collections.emptyList());
    }

    List<SaleProduct> filteredProducts = saleProducts.stream()
        // category가 null이거나 ALL이면 통과, 아니면 일치하는 카테고리만 필터링
        .filter(product -> isCategoryMatch(product, category))
        .collect(Collectors.toList());

    return SaleProductListResponse.of(store, filteredProducts);
  }

  private boolean isCategoryMatch(SaleProduct product, ProductCategory category) {
    if (category == null || category == ProductCategory.ALL) {
      return true;
    }
    return product.getCategory() == category;
  }
}