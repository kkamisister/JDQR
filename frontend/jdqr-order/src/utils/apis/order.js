import axiosInstance from "./axiosInstance";

/**
 * 장바구니 담기 함수
 * @param {Object} cartItem - 장바구니에 담을 아이템 정보
 * @param {number} cartItem.userId - 유저 ID
 * @param {number} cartItem.dishId - 메뉴 ID
 * @param {string} cartItem.dishName - 상품명
 * @param {number} cartItem.dishCategoryId - 카테고리 ID
 * @param {string} cartItem.dishCategoryName - 카테고리명
 * @param {Array<number>} cartItem.choiceIds - 옵션 IDs
 * @param {number} cartItem.price - 가격
 * @param {number} cartItem.quantity - 수량
 */
const addToCart = async (cartItem) => {
  try {
    // WebSocket 객체 생성 및 연결
    const socket = new WebSocket(
      "wss://jdqr608.duckdns.org/api/v1/order/cart/add"
    );

    // 연결이 성공적으로 이루어졌을 때 실행
    socket.onopen = () => {
      console.log("WebSocket 연결 성공");

      // 장바구니 아이템 정보를 JSON 형식으로 변환 후 WebSocket을 통해 전송
      socket.send(JSON.stringify(cartItem));
    };

    // 서버로부터 메시지를 수신했을 때 실행 (필요에 따라 응답 처리)
    socket.onmessage = (event) => {
      const response = JSON.parse(event.data);
      console.log("서버 응답:", response);
      // 필요에 따라 서버 응답 처리 가능
    };

    // 에러 발생 시 실행
    socket.onerror = (error) => {
      console.error("WebSocket 에러:", error);
    };

    // 연결이 닫혔을 때 실행
    socket.onclose = () => {
      console.log("WebSocket 연결이 종료되었습니다.");
    };
  } catch (error) {
    console.error("장바구니 담기 오류:", error);
  }
};

export default addToCart;
