// axiosInstance.js

import axios from "axios";

const dummyToken = "dummyTableToken";
const tableId = "6721aa9b0d22a923091eef73";

const axiosInstance = axios.create({
  baseURL: "https://jdqr608.duckdns.org/api/v1/",
  timeout: 1000,
  withCredentials: true,
  headers: {
    Authorization: `Bearer ${dummyToken}`,
  },
});

// // tableId와 token을 URL에서 추출하여 저장
// axiosInstance.extractTableInfo = () => {
//   const params = new URLSearchParams(window.location.search);
//   const tableId = params.get("tableId");
//   const token = params.get("token");
//   if (tableId && token) {
//     localStorage.setItem("tableId", tableId);
//     localStorage.setItem("tableToken", token);
//     console.log("tableId와 token이 성공적으로 저장되었습니다.");
//   } else {
//     console.warn("URL에서 tableId 또는 token을 찾을 수 없습니다.");
//   }
// };

// 쿠키 발급 함수
// axiosInstance.setUserCookie = async () => {
//   const token = localStorage.getItem("tableToken");

//   if (!token) {
//     console.error("토큰이 존재하지 않아 쿠키 발급 요청을 할 수 없습니다.");
//     return;
//   }

//   try {
//     const response = await axiosInstance.get("order/cart/cookie", {
//       headers: {
//         Authorization: `Bearer ${token}`,
//       },
//     });
//     console.log("쿠키 발급 성공");
//     return response.data;
//   } catch (error) {
//     console.error("쿠키 발급 오류:", error);
//     throw new Error("쿠키 발급 중 오류가 발생했습니다.");
//   }
// };

axiosInstance.setUserCookie = async () => {
  const token = localStorage.getItem("tableToken") || dummyToken; // localStorage에 token이 없을 경우 dummyToken 사용

  if (!token) {
    console.error("토큰이 존재하지 않아 쿠키 발급 요청을 할 수 없습니다.");
    return;
  }

  try {
    const response = await axiosInstance.get("order/cart/cookie", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log("쿠키 발급 성공");
    return response.data;
  } catch (error) {
    console.error("쿠키 발급 오류:", error);
    throw new Error("쿠키 발급 중 오류가 발생했습니다.");
  }
};
export default axiosInstance;
