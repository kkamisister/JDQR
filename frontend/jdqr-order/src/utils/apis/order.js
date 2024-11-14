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
