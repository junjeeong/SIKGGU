package dev.junyeong.sikggu.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsumerService {

  private final OrderService orderService;
  private final PaymentService paymentService;

  /**
   * 주문 생성 (예약하기) 및 결제 처리 로직을 조정합니다. 데이터의 무결성을 생각하여 주문과 결제를 하나의 트랜잭션에서 담당합니다.
   */
  @Transactional
  public OrderResponse placeOrder(Long userId, OrderRequest request) {
    // 1. 유효성 검사 (상품 존재 여부, 가격 등)
    // 2. ⭐ 주문 생성 및 재고 차감 (OrderService 책임) ⭐
    OrderResponse orderResponse = orderService.createOrder(userId, request);

    // 3. ⭐ 결제 처리 (PaymentService 책임) ⭐
    //    실제 PG사 연동 로직은 PaymentService에서 담당
    // paymentService.processPayment(userId, orderResponse.orderId(), request.paymentInfo());

    // 4. (부가 로직) 쿠폰 사용, 포인트 차감, 알림 발송 등

    return orderResponse;
  }

  /**
   * 사용자의 전체 주문/예약 내역을 조회합니다.
   */
  public OrderListResponse getOrders(Long userId) {
    // OrderService에 단순 조회 책임을 위임합니다.
    return orderService.getOrdersByUserId(userId);
  }

  /**
   * 특정 주문의 상세 정보를 조회합니다.
   */
  public OrderResponse getOrderDetail(Long userId, Long orderId) {
    return orderService.getOrderDetail(userId, orderId);
  }

  /**
   * 주문(예약)을 취소하고 재고를 복구하는 로직을 조정합니다.
   */
  @Transactional
  public void cancelOrder(Long userId, Long orderId) {
    // 1. 주문 상태 변경 및 유효성 검사 (OrderService 책임)
    orderService.cancelOrder(userId, orderId);

    // 2. 결제 취소/환불 로직 (PaymentService 책임)
    // paymentService.cancelPayment(orderId);

    // 3. (부가 로직) 사용된 쿠폰/포인트 복구, 알림 발송
  }


  public PaymentListResponse getPayments(Long userId) {
    return paymentService.getPaymentsByUserId(userId);
  }

}