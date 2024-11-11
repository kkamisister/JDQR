import axiosInstance from "./axiosInstance";

/**
 * 메뉴판 조회
 * @returns {Object} - 메뉴판 정보가 담긴 data 객체
 */
export const fetchDishMenu = async () => {
  try {
    const response = await axiosInstance.get("/dish");
    // console.log(response.data.data.tableName);
    return response.data.data; // 실제 메뉴판 정보만 반환
  } catch (error) {
    console.error("메뉴판 조회 실패:", error);
    throw new Error("메뉴판 조회 중 오류가 발생했습니다.");
  }
};

/**
 * 전체 메뉴 카테고리 조회
 * @returns {Object}
 */
