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
    return response;
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
    return response;
  } catch (error) {
    throw new Error("흠냐 주문 상태 조회가 안되는데;;");
  }
};
