import axiosInstance from "./axiosInstance";

/**
 * 테이블 이름 조회
 * @returns {Object}
 */

export const getTableName = async () => {
  try {
    const response = await axiosInstance.get("owner/table/name");
    return response.data.data.tableName;
  } catch (error) {
    throw new Error("흠냐 테이블 이름 안 가져와지는데?");
  }
};
