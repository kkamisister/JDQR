## 1. 프로젝트 개요

기존 포스기기/키오스크 기반 주문 방식은 각종 수수료와 서비스 이용료 등 자영업자의 부담을 가중시키는 측면이 있었습니다. 또한 포스연동과 결제, 더치페이를 모두 지원하는 서비스가 존재하지 않았습니다.

 좋댓QR은 이러한 기능을 모두 포함하는 통합형 주문 서비스입니다. 또한, 가맹점의 최신 가격이 반영된 지도 UI를 제공하고, POS연동 지도를 통해 유저가 매장의 잔여 테이블 현황을 편리하게 확인할 수 있도록 돕습니다.

## 2. 서비스 개요

좋댓QR은 QR코드 기반 테이블 오더 서비스를 제공합니다. 고객은 매장 내의 QR코드를 스캔하여 메뉴판에 접속 및 주문을 할 수 있고, 더치페이를 포함한 간편결제가 가능합니다. 

### 매장 관리 기능(For 점주)

- **테이블 관리**: 점주는 매장 내에 테이블을 추가하고 삭제할 수 있습니다. 추가 시 해당 테이블의 착석 가능 인원을 설정하고, 지도의 좌석 현황에 반영할 수 있습니다.
    - **테이블 별 QR 관리 :** 점주는 테이블 별로 고객의 주문 기능을 제공하는 QR 코드를 생성할 수 있습니다. 생성한 QR을 이미지로 저장 또는 즉석 프린트를 할 수 있고, 유출의 우려가 있을 경우 즉각적으로 새로운 QR을 발급 받을 수 있습니다.
    - **테이블 별 주문 관리** : 테이블 별로 실시간 주문 상태를 확인할 수 있습니다.
- **메뉴 카테고리/옵션 추가**: 메뉴가 속하는 그룹인 메뉴 카테고리와, 메뉴에서 선택 가능한 옵션들을 추가 / 삭제 / 수정 할 수 있습니다.
- **메뉴 추가** : 기존에 추가한 메뉴 카테고리와 옵션을 바탕으로, 메뉴를 등록하고 삭제 또는 수정할 수 있습니다.

### 테이블 오더(For 손님)

- **주문하기** : 유저는 QR코드를 스캔하여 주문 화면으로 이동한 뒤, 원하는 메뉴를 주문할 수 있습니다. 주문의 경우 유저들이 각자 장바구니에 메뉴를 담은 후, 장바구니의 메뉴를 한 번에 주문하는 식으로 이루어집니다. 여러 유저들이 각자의 폰을 이용해 동시에 장바구니에 메뉴를 담는 것이 가능하며, 자신이 담은 메뉴를 구분해서 볼 수 있습니다.
- **결제하기** : 유저는 1/N 방식 결제 혹은 메뉴별 결제 중 하나를 선택하여 결제를 진행할 수 있습니다. 결제 이후 결제가 완료되었다는 알림이 WebSocket을 통해 결제 중인 다른 유저들에게 전송됩니다. 결제가 진행되는 동안은 다른 페이지 이동 및 추가 주문이 제한됩니다. **결제 방식(더치페이/메뉴별 결제)은 혼용할 수 없습니다.** 전체 금액이 모두 정산될 시 결제 상태가 완료로 변경되며 성공창 이후 메인화면으로 리다이렉트 됩니다.
    - 금액별 결제(더치페이) : 전체 금액에 대해 N/M을 조절하여 결제할 수 있습니다. 설정하지 않을 시 기본 비율은 1/N(주문 참여자 수)로 지정됩니다.
    - 메뉴별 결제: 각 주문 내역에 대해 메뉴와 결제 수량을 선택해 결제할 수 있습니다. 각 메뉴에 대해 내가 담은 메뉴만 모아보는 필터링 기능을 제공합니다.
- **메뉴판**: 메뉴의 사진, 설명, 금액 등이 제공됩니다. 각 메뉴를 선택 시 상세 보기로 이동하며, 상세보기에서 수량. 옵션 선택 및 주문을 진행할 수 있습니다.
- **장바구니:** 마음에 드는 메뉴들을 담을 수 있습니다. 장바구니에서 주문하기 버튼을 누르는 순간 담아놓은 메뉴들에 대해 POS에 주문이 들어가게 됩니다.
- **주문내역**: 현재까지 주문한 내역들을 볼 수 있습니다. 한번에 주문한 항목끼리 모아서 주문별 결제 총액, 수량 등을 보여줍니다.

### 매장위치, 잔여 좌석 현황 확인(For 매장 外 고객)

- 매장의 QR이 아닌, 어플로 접근한 미가입 고객의 경우 회원가입 없이 지도 기능을 이용할 수 있습니다.
- **매장 위치 확인** : Kakao map api를 이용한 지도 서비스에 좋댓큐알 가맹점의 정확한 위치를 동기화 하여 식당 위치 정보를 제공합니다.
    - 한식, 양식, 중식, 일식, 카페, 술집 등 카테고리로 매장 필터링 기능을 제공합니다.
    - 사용자의 현재 위치를 기반으로, 사용자는 최초 접속 시 주변 가맹점 현황을 확인할 수 있습니다.
    - 지도 확대, 축소 시 가맹점 클러스터링으로 지도 이용 시 더 나은 사용자 경험을 제공합니다.
- **실시간 잔여 좌석 현황 확인** : 가맹점의 잔여 테이블과 잔여 좌석 현황 정보를 실시간으로 제공합니다.
    - 사용자는 일행 인원수와 한 테이블에 함께 앉기 여부를 선택하여 실시간으로 바로 착석이 가능한 식당을 필터링 할 수 있습니다.

### 메뉴판, 가격 최신화

- **메뉴 상세 정보 확인** : 지도에서 특정 메장 클릭 시 해당 매장에서 제공하는 전 메뉴 정보를 확인 가능
- **메뉴별 가격 최신화** : 지도에서 제공하는 가맹점의 제공 메뉴 정보를 실제 POS기와 연동하여, 메뉴, 상세 옵션 별 실제 최신화 된 가격 정보 제공

## 3. 주요 기술 및 아키텍처

### Redis 분산 락

- 결제는 동시성 이슈에 민감한 비즈니스 로직임.
- 하나의 메뉴에 대해서 중복해서 결제가 되지 않도록 하기 위해, 락 도입을 고려
- DB가 여러 개로 나누어져 있어도 동시성 이슈를 해결할 수 있는 분산락을 도입
- 분산락을 구현할 수 있는 DB는 여러 개가 있으나, 이 중 친숙한 Redis를 사용하기로 결정. Redis를 여러 개로 두어 관리하며, 동시성을 해결하는 Redisson 알고리즘 사용(Redis가 하나일 경우, 이 부분이 bottle neck이 되기 때문)
- 분산 락을 위한 어노테이션인 **@RedLock**을 구현하여 사용하였다
    - 예시
        
        ```jsx
        package com.example.backend.common.aop;
        
        import com.example.backend.common.annotation.RedLock;
        import lombok.RequiredArgsConstructor;
        import org.aspectj.lang.ProceedingJoinPoint;
        import org.aspectj.lang.annotation.Around;
        import org.aspectj.lang.annotation.Aspect;
        import org.aspectj.lang.reflect.MethodSignature;
        import org.redisson.api.RLock;
        import org.redisson.api.RedissonClient;
        import org.springframework.context.ApplicationEventPublisher;
        import org.springframework.expression.ExpressionParser;
        import org.springframework.expression.spel.standard.SpelExpressionParser;
        import org.springframework.expression.spel.support.StandardEvaluationContext;
        import org.springframework.stereotype.Component;
        import org.springframework.transaction.event.TransactionPhase;
        import org.springframework.transaction.event.TransactionalEventListener;
        
        @Aspect
        @Component
        @RequiredArgsConstructor
        public class RedLockAop {
            private final RedissonClient redissonClient;
            private final ApplicationEventPublisher applicationEventPublisher;
        
            @Around("@annotation(com.example.backend.common.annotation.RedLock)")
            public Object lock(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
                MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
                RedLock redLock = signature.getMethod().getAnnotation(RedLock.class);
        
                RLock lock = redissonClient.getLock(getDynamicValue(signature.getParameterNames(), proceedingJoinPoint.getArgs(), redLock.key()).toString());
                try {
                    if (lock.tryLock(redLock.waitTime(), redLock.leaseTime(), redLock.timeUnit())) {
                        return proceedingJoinPoint.proceed();
                    } else {
                        throw new RuntimeException();
                    }
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        applicationEventPublisher.publishEvent(lock);
                    }
                }
            }
        
            public static Object getDynamicValue(String[] parameterNames, Object[] args, String key) {
                ExpressionParser parser = new SpelExpressionParser();
                StandardEvaluationContext context = new StandardEvaluationContext();
                for (int i = 0; i < parameterNames.length; i++) {
                    context.setVariable(parameterNames[i], args[i]);
                }
                return parser.parseExpression(key).getValue(context, Object.class);
            }
        
            @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
            public void test3(RLock rLock) {
                rLock.unlock();
            }
        }
        ```
        
        마지막에 **Lock**을 반납할 때는, **@TransactionalEventListener** 를 활용하여 확실하게 커밋이 된 이후에 락이 반납되도록 하였다.
        
        이렇게 하지 않으면, 커밋이 되기  이전에 락이 반납이 되서 **동시성** 문제가 발생할 여지가 있다
        
    

### PINPOINT

- 서버의 상태를 모니터링 하기 위한 도구
- 이번 프로젝트에 처음 도입하여, 프론트에서 예외가 발생한 request를 빠르게 확인할 수 있었음
- 또한 DB query가 얼마나 나가는지 재귀적으로 확인이 가능하여, 시간이 오래걸리던 API를 성능개선할 수 있었다.
    - 점주 테이블 조회 API에서 현재 주문목록을 받아오는것이 필요했는데, 여기서 많은 JOIN에 의한 다중 쿼리 발생을, Value Object를 커스터마이징 하여 한방 쿼리로 해결할 수 있었다

### TestContainer

- 테스트 환경에 구애받지 않고 테스트의 멱등성을 유지할 수 있도록 해주는 도구
- 테스트 코드를 Docker 환경에서 필요한 의존성 이미지를 받아와서 테스트에 사용
- 중복된 테스트 코드가 무수히 생산되는 문제가 있어서.. 반복되는 테스트 데이터를 생성하기 위한 **TestDataGenerator** 클래스를 생성하여 단위테스트를 진행하였고, 기존 코드를 **1/5**로 줄일 수 있었다

**QR 코드 링크에 토큰 적용**

- QR코드의 기술상 한계로 인하여, QR을 찍는 초기 URL이 노출되는 경우 이를 막을 수 있는 수단이 없었다
    - 이를 위해 식당의 암구호를 적용하거나 위치를 받아서 식당에 있는 경우에만 접속할 수 있도록 하기 등의 방안이 나왔으나, 현실적이지 않고 위치가 정확하지 않아 발생할 수 있는 문제가 있었다.
    - 최소한 한번 사용했던 QR Link에 대해서 집에서 재접속 하는 경우를 막기 위해서 테이블 별 고유 URL을 설정하였고 프론트에서 서버로 QR을 찍으면 url에 만료기한을 가진 tableToken을 덧붙인 새로운 url을 발급하도록 하였다
    - 추후 개선할 수 있는 점이 있다면, **`fingerprint.js`** 라는 외부 라이브러리를 활용하여 사용자의 컴퓨터를 90%이상의 정확도로 구분해낼 수 있다고 한다
    - 현재 서비스에서는 해당 부분을 점주가 QR을 재생성 하는 기능과 이를 프린팅 하는 기능을 점주 UI에 제공하여 악의적 주문이 있는 경우 새롭게 QR을 제작할 수 있도록 하였음

### WebSocket & Stomp 프로토콜

- 기존에는 유저가 장바구니에 품목을 담고 이를 해당 장바구니에 접속해있는 모든 유저에게 해당 유저가 품목을 담았다는 사실을 전달하기 위해 **`SSE`** 방식을 사용했었다.
    - 그러나 곰곰히 생각해보니, SSE를 통해 다른 유저가 품목을 담았다는 사실을 받으면서 정작 본인이 물품을 담거나 삭제하는 경우에는 Http 요청을 보내서 서버의 상태를 변경하는 점이 마음에 들지 않았다
    - 그래서 단방향 소통이 아닌, 양방향 소통에 장점을 가지는 **`WebSocket`** 을 도입하였고 주고받는 메세지에 미리 정해진 커맨드를 통해 의미를 전달할 수 있는 **`Stomp`**  프로토콜을 사용하였다.
        - Stomp를 통해 유저가 서버를 SUBSCRIBE , CONNECT, DISCONNECT 하는 경우에 대해 좀 더 쉽게 서버측에서 케이스를 나눠 구현할 수 있었다.
        - Stomp를 사용하여 스프링 서버를 broker로 둔 Pub/Sub 구조로 사용자와 소통할 수 있었다
    - 웹소켓을 사용 시 http에서 사용하는 **`GlobalExceptionHandler`** 를 통해 예외를 던지게 되면, 해당 예외는 웹소켓 연결이 끊기는 순간에 클라이언트에게 전달이 된다.
    - @Valid 어노테이션을 활용하여 웹소켓 연결과정 속에서도 유저가 올바르지 않은 요청을 보내는 경우에 대해 예외처리를 하기 위해 **`@GlobalMessageExceptionHandler`** 를 사용하였고, 이를 프론트에서 **`/queue/error`** 라는 경로로 구독하게 하여 예외를 일대일로 전달할 수 있었다
        - 참고로 /queue 는 미리 정해진 예약어로써, 서버가 특정 유저에게 1:1로 데이터를 전달할 수 있는 통로라고 한다
        - 다만 이를 위해서는,  **customHandShakeHandler**를 등록해야한다
        - 코드
            
            ```jsx
            //클라이언트에서 연결할 엔드포인트 설정
            		registry.addEndpoint("/ws")
            			.setAllowedOrigins("http://localhost:3000","https://jdqr608.duckdns.org","https://jdqr608.duckdns.org:8081")
            			.setHandshakeHandler(new CustomHandshakeHandler());
            			
            			
            			
            			
            // 실제 CustomHandshakeHandler
            
            package com.example.backend.common.handler;
            
            import java.util.Map;
            import java.util.UUID;
            
            import org.springframework.http.server.reactive.ServerHttpRequest;
            import org.springframework.web.socket.WebSocketHandler;
            import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
            
            import com.example.backend.common.util.StompPrincipal;
            
            public class CustomHandshakeHandler extends DefaultHandshakeHandler {
            	protected StompPrincipal determineUser(ServerHttpRequest request,
            		WebSocketHandler wsHandler,
            		Map<String, Object> attributes) {
            		return new StompPrincipal(UUID.randomUUID().toString());
            	}
            
            }
            
            ```
            
    - **예외처리 사용 예시**
        
        ```jsx
        	@Operation(summary = "장바구니 항목 담기", description = "장바구니에 항목을 담는 api")
        	@MessageMapping("/cart/add")
        	public void addItemToCart(@Payload CartDto productInfo, @Header("simpSessionAttributes") Map<String,Object> attributes){
        
        		// 수동 검증 수행
        		Set<ConstraintViolation<CartDto>> violations = validator.validate(productInfo);
        		if (!violations.isEmpty()) {
        			// 유효성 검증 실패 예외 발생
        			List<String> errorMessages = violations.stream()
        				.map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
        				.collect(Collectors.toList());
        
        			throw new ValidationException(errorMessages); // 유효성 검증 실패 예외 발생
        		}
        
        		String tableId = (String)attributes.get("tableId");
        		log.warn("테이블 id : {}",tableId);
        		orderService.addItem(tableId, productInfo);
        	}
        ```
        
        @Valid 어노테이션이 일반적으로 동작하지 않기 때문에, 해당 예외를 Validator를 사용하여 직접 잡아서 GlobalMessagingHandler에서 구현한 예외로 던졌다
