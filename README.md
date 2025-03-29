# 🧾 좋댓QR - QR 기반 테이블 오더 통합 서비스

## 📌 1. 프로젝트 개요

기존 포스기기/키오스크 기반의 주문 방식은 수수료와 서비스 이용료 등의 부담을 자영업자에게 전가했습니다.  
또한, 포스 연동 + 결제 + 더치페이를 모두 지원하는 서비스는 부재한 상황이었습니다.

**좋댓QR**은 이 모든 기능을 통합한 **QR 기반 테이블 오더 서비스**입니다.

- 가맹점의 **최신 가격**이 반영된 **지도 UI**
- **POS 연동 지도**를 통해 **실시간 잔여 테이블 확인**

## 🛠️ 2. 서비스 개요

### 💼 점주 기능

- **테이블 관리**: 테이블 추가/삭제, 착석 인원 설정, 지도 연동
- **테이블별 QR 관리**: QR 생성 및 이미지 저장/프린트, 재발급
- **테이블 주문 상태 확인**: 실시간 주문 현황
- **메뉴 카테고리/옵션/메뉴 관리**

### 👤 고객 기능

#### 주문 & 결제

- **주문 방식**: QR 스캔 → 장바구니 담기 → 단체 주문
- **결제 방식**:
  - 금액 비례 결제 (더치페이, 기본은 1/N)
  - 메뉴별 결제 (본인이 고른 메뉴만 선택 가능)
- **WebSocket 실시간 알림**: 결제 상태 동기화, 페이지 이동 제한

#### 기타 기능

- **메뉴판**: 이미지 + 설명 + 옵션 선택
- **장바구니**: 공동 장바구니 → POS 주문 전송
- **주문 내역**: 주문별 항목/총액 확인

### 🗺️ 매장 지도 및 잔여석 확인

- **회원가입 없이 지도 접근 가능**
- **Kakao Map API 연동**
  - 음식점 카테고리 필터
  - 사용자 위치 기반 주변 매장 탐색
  - 클러스터링으로 UX 개선
- **잔여 좌석 필터**: 인원수/같은 테이블 착석 조건 설정 가능

### 🧾 메뉴판, 가격 최신화

- 지도 → 매장 클릭 → 메뉴 상세 정보 확인
- **POS와 연동된 실시간 가격 정보 제공**

## ⚙️ 3. 주요 기술 및 아키텍처

### ✅ Redis 분산 락

- 결제 동시성 문제 해결
- **Redisson + @RedLock AOP** 적용
- **트랜잭션 커밋 이후 락 반납** (@TransactionalEventListener)

### ✅ PINPOINT

- 서버 모니터링 및 DB 쿼리 추적
- 다중 JOIN API 성능 개선 (예: 점주 테이블 조회 → VO 활용)

### ✅ TestContainer

- 테스트 환경 자동화 (Docker + 이미지 사용)
- 테스트 데이터 자동 생성기 활용 → 코드량 1/5로 감소

### 🔒 QR 코드 URL 보안

- 고정 URL 노출 문제 해결
- **tableToken + 만료기한** 추가 발급 방식 적용
- 추후 개선안: `fingerprint.js` 활용 사용자 식별 가능

### 🔁 WebSocket + STOMP 프로토콜

- **양방향 통신**: 주문 및 알림을 실시간 처리
- **@GlobalMessageExceptionHandler**로 유효성 검증 처리
- **/queue/error** 경로로 클라이언트 개별 예외 알림

### 📦 기타

- `JavaTimeModule` 등록 (Redis, JSON, WebSocket)
- `@JsonInclude(Include.NON_NULL)`로 응답 최적화
- `fetchFirst()` vs `fetchOne()` 차이 정리
- DTO 해시 코드 커스터마이징 (dishId, userId, optionsId 기반)
- JS 쿠키 접근 제약 → `httpOnly`, domain 설정 주의

### 💳 결제 로직 (Toss Payments SDK)

- Toss SDK로 안전한 결제 처리  
![image__12_](https://github.com/user-attachments/assets/449ae88e-7b49-43ab-a407-7dfa909eabf8)

### 💡 Optimistic UI 적용

- 수량 변경 → 즉시 UI 반영 → 서버 응답으로 검증 후 UI 재조정

### 🏗️ 서비스 아키텍처

![image__13_](https://github.com/user-attachments/assets/faa53f13-7cca-4e0e-90a1-0bd1d4058ea0)

## 🖥️ 4. 사용 화면

| 구분 | 이미지 |
|------|--------|
| 점주 로그인 후 테이블/메뉴 확인 | ![1 _점주_로그인_후_테이블과_메뉴확인](https://github.com/user-attachments/assets/cffa6590-cc1f-4b10-b162-9ac8e303145b) |
| 테이블 추가 | ![2 _테이블_추가](https://github.com/user-attachments/assets/f92cffb9-97c9-4c6c-b08c-ba1a24fac78f) |
| 테이블 상세/QR 재생성 | ![3 _테이블_상세정보_QR재생성_수정](https://github.com/user-attachments/assets/8ec55cf7-4c0b-4c91-abb4-bd31687b8924) |
| 메뉴 설정 | ![4 _메뉴설정화면](https://github.com/user-attachments/assets/5203b15e-991d-4e63-84c6-c6c2beec0c32) |
| 지도에서 식당 확인 | ![5 _지도에서_식당확인](https://github.com/user-attachments/assets/067c8bd1-bde7-427b-afe1-36798888669b) |
| 일행과 동시주문 | ![78 _일행과_동시주문](https://github.com/user-attachments/assets/f4c2d6c6-57e3-4186-9457-13061fd3f14a) |
| 결제 화면 | ![image__15_](https://github.com/user-attachments/assets/4a23b89c-59fe-49f2-9888-f9a9937db971) |
| 고객 핵심 기능 | ![image__16_](https://github.com/user-attachments/assets/1ae0d1bd-a939-42a6-92a4-2cf410e8a25b)<br>![image__14_](https://github.com/user-attachments/assets/d9075d7d-bb08-4353-aece-7302f0bfb872)|

## 🧠 5. 결론

좋댓QR은 **정확한 정보**와 **편리한 UX**를 통해  
식당 탐색 → 주문 → 결제의 전 과정을 **통합 관리**할 수 있게 해줍니다.  
테이블 오더 + 잔여 테이블 현황 + 실시간 메뉴/가격 연동 = **고객과 점주 모두에게 효율적인 솔루션**

## 🎨 6. UI/UX 기획

🔗 [Figma 디자인 보기](https://www.figma.com/design/paoPdGGVdWBKbgWYgUtBml/SSAFY-11기-자율-프로젝트(주문-서비스)?node-id=4061-1359)

## 🗃️ DB 설계

🔗 [MySQL ERD 보기](https://www.erdcloud.com/d/7KE5MkJjDW3cQuJ6P)  
![erd](https://github.com/user-attachments/assets/3e8bc50e-25e4-4a0c-9501-1317abfb10f9)


## 🎥 UCC

🔗 [좋댓QR 소개 영상](https://youtu.be/cPtMN2Ho9ws)
