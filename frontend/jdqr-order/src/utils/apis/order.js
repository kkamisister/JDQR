import axiosInstance from "./axiosInstance";

/**
 * 주문내역 조회
 * @returns {Object}
 */

export const fetchOrderList = async () => {
  try {
    const response = await axiosInstance.get("order");
    return response.data.data;
  } catch (error) {
    console.log("error:", error);
    throw new Error("쓰읍..이거 주문 내역 조회가 안 되는데?");
  }
};

/**
 * 주문하기
 */
export const placeOrder = async () => {
  try {
    const response = await axiosInstance.post("order");
    // console.log(response);
    return response;
  } catch (error) {
    throw new Error("이거 주문이 안되는데용???ㅠ,ㅠ");
  }
};

/**
 * 주문상태 변경
 * @returns {Object}
 */

export const ChangeOrderStatus = async () => {
  try {
    const response = await axiosInstance.post("order/status");
    return response.data;
  } catch (error) {
    throw new Error("야;; 주문 상태 변경이 안된다..");
  }
};

/**
 * 주문 상태 조회
 * @returns {Object}
 */

export const checkOrderStatus = async () => {
  try {
    const response = await axiosInstance.get("order/status");
    return response.data;
  } catch (error) {
    throw new Error("흠냐 주문 상태 조회가 안되는데;;");
  }
};

/**
 * 결제 내역 조회
 * @returns {Object}
 */

export const fetchPaymentList = async () => {
  try {
    const response = await axiosInstance.get("order/payment");
    return response.data.data;
  } catch (error) {
    throw new Error("헉스바리 결제 진행 내역 조회가 안 돼요");
  }
};

/**
 * N빵 결제 요청 (Money Divide)
 * @param {Object} params
 * @param {number} params.peopleNum - 전체 인원
 * @param {number} params.serveNum - 결제할 몫의 수
 * @returns {Object}
 */
export const moneyDivide = async ({ peopleNum, serveNum }) => {
  try {
    const orderResponse = fetchOrderStatus();
    if (orderResponse.orderStatus === "PENDING") {
      changeOrderStatus();
    } else if (orderResponse.orderStatus === "CANCELLED") {
      window.alert("취소된 주문이라 결제가 종료되었습니다.");
      return;
    } else if (orderResponse.orderStatus === "PAID") {
      window.alert("결제가 완료되어 더 이상 결제를 진행할 수 없습니다.");
      return;
    }
    const response = await axiosInstance.post("order/payment", {
      type: "MONEY_DIVIDE",
      peopleNum,
      serveNum,
    });

    return response.data.data;
  } catch (error) {
    throw new Error("으악! N빵 결제 안 돼");
  }
};

/**
 * @param {Object} params
 * @param {Array} params.orderItemInfos
 * @returns {Object}
 */
export const menuDivide = async ({ orderItemInfos }) => {
  try {
    const orderResponse = await fetchOrderStatus();

    if (orderResponse.orderStatus === "PENDING") {
      changeOrderStatus();
    } else if (orderResponse.orderStatus === "CANCELLED") {
      window.alert("취소된 주문이라 결제가 종료되었습니다.");
      return;
    } else if (orderResponse.orderStatus === "PAID") {
      window.alert("결제가 완료되어 더 이상 결제를 진행할 수 없습니다.");
      return;
    }
    const response = await axiosInstance.post("order/payment", {
      type: "MENU_DIVIDE",
      orderItemInfos,
    });

    return response.data.data;
  } catch (error) {
    throw new Error("엉엉 아이템별 결제 안돼");
  }
};

/**
 * 주문 상태 변경
 */
export const changeOrderStatus = async () => {
  try {
    const response = await axiosInstance.post("order/status");
    return response;
  } catch (error) {
    console.log("주문상태 변경 에러", error);
  }
};

/**
 * 주문 상태 조회
 */
export const fetchOrderStatus = async () => {
  try {
    const response = await axiosInstance.get("order/status");
    return response.data.data;
  } catch (error) {
    console.log("주문 상태 조회 중 에러 발생");
  }
};
