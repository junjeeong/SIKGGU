# 🥦 SIKGGU

**식구, 식재료를 구출하라!**  

동네 마트에서는 매일 멀쩡하지만 유통기한이 임박한 식재료들이 폐기되고, 1인 가구는 비싼 식재료 물가에 고통받습니다. **SIKGGU**는 이 두 가지 문제를 '위치 기반 실시간 타임세일'로 연결하여 해결하는 O2O 커머스 서비스입니다.

## 👥 타겟(Pain Point)

- 마트 사장님 : “반품도 안 되는 신선식품, 오늘 못 팔면 쓰레기가 된다.”
- 자취생/1인 가구 : “된장 찌개 해 먹고 싶은데… 애호박 하나에 3천 원이라니...”

## 🚀 핵심 기능

- **임박/B급 식재료 등록**: 유통 기한이 얼마 남지 않았거나, B급(예: 상태가 좋지 않은 버섯 등) 식재료를 보다 저렴한 가격으로 등록합니다.
- **위치 기반 실시간 알림**: 상품을 등록하면 설정된 근방 N(km) 내에 있는 회원들에게 실시간으로 알림을 보냅니다.
- **선착순 구매 및 방문 포장**: 알림을 받은 회원이 선착순으로 예약합니다. (결제 process는 추후 구현 과제)

## 🏁 도전 과제

- **재고 동시성 제어  :** 마감 임박 '반값 족발' 재고가 딱 1개 남은 상황에서 10명의 자취생이 동시에 **[예약하기]** 버튼을 눌렀습니다.
- **위치 기반 조회 최적화 :** "내 근처 마트에서 내놓은 식자재"를 어떻게 효율적으로 빨리 찾느냐?
- **상태 관리 및 스케줄링  :** "마감 시간이 지나면 사용자에게 노출되지 않고 빠르게 삭제되는가?”

## 🛠️ 기술스택
<img width="500" height="600" alt="image" src="https://github.com/user-attachments/assets/54dcb0a6-e03c-402d-854b-476a1ef6e031" />


## 📑  ERD
<img width="600" height="800" alt="image" src="https://github.com/user-attachments/assets/918c447c-7dc7-43dd-b39e-265d350baf2b" />


## 🔗 API 엔드포인트

### 인증 및 회원

| **Method** | **Endpoint** | **설명** | **핵심 요청/응답 데이터** |
| --- | --- | --- | --- |
| **POST** | `/api/v1/auth/sign-up` | 회원가입 | `email`, `password`, `role` (STORE/USER), `nickname` |
| **POST** | `/api/v1/auth/sign-in` | 로그인 | `email`, `password` → **AccessToken발급** |

### 상점 관리

| **Method** | **Endpoint** | **설명** | **핵심 요청/응답 데이터** |
| --- | --- | --- | --- |
| **POST** | `/api/v1/stores` | 가게 등록 (사장님) | `storeName`, `address`, **`latitude`, `longitude`** |
| **GET** | `/api/v1/stores/me` | 내 가게 조회 | `storeId`, `storeName`, 운영 상태 등 |

### 상품

| **Method** | **Endpoint** | **설명** | **핵심 요청/응답 데이터** |
| --- | --- | --- | --- |
| **POST** | `/api/v1/products` | 타임세일 상품 등록 | `storeId`, `name`, `price`, `discountPrice`, `stock`, **`deadline(마감시간)`** |
| **GET** | `/api/v1/products` | **내 주변 상품 조회** | Query Param: **`lat`, `lon`, `radius(km)`**, `sort` (거리순/마감임박순) |
| **GET** | `/api/v1/products/{id}` | 상품 상세 조회 | 상품 상세 정보, **현재 남은 재고 수량** |
| **DELETE** | `/api/v1/products/{id}` | 상품 삭제/마감 처리 | (스케줄러에 의해 자동 처리되거나 사장님이 수동 삭제) |

### 주문

| **Method** | **Endpoint** | **설명** | **핵심 요청/응답 데이터** |
| --- | --- | --- | --- |
| **POST** | `/api/v1/orders` | **상품 예약/구매** | `productId`, `quantity` → **동시성 제어 필수 구간** |
| **GET** | `/api/v1/orders` | 내 주문 내역 조회 | `orderId`, `productName`, `status` (RESERVED, PICKED_UP, CANCELED) |
| **PATCH** | `/api/v1/orders/{id}/pickup` | 방문 수령 완료 처리 | `orderId` (사장님이 QR 체크 등으로 상태 변경 시 호출) |
