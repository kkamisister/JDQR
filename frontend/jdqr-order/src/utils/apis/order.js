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
