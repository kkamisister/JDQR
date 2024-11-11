import axiosInstance from "./axiosInstance";

/**
 * 메뉴판 조회
 * @returns {Object} - 메뉴판 정보가 담긴 data 객체
 */
export const fetchDishMenu = async () => {
  try {
    const response = await axiosInstance.get("dish");
    // console.log(response.data.data.tableName);
    return response.data.data; // 실제 메뉴판 정보만 반환
  } catch (error) {
    console.error("메뉴판 조회 실패:", error);
    throw new Error("메뉴판 조회 중 오류가 발생했습니다.");
  }
};

/**
 * 메뉴 상세 조회
 * @returns {Object}
 */
export const fetchDishDetail = async (dishId) => {
  try {
    const response = await axiosInstance.get(`dish/${dishId}`);
    return response.data.data;
  } catch (error) {
    console.log("메뉴 상세 조회 실패:", error);
    throw new Error("아놔 메뉴 상세조회하다 오류발생 ㄱㅡ");
  }
};
