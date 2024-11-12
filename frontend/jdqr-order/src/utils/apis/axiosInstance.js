import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "https://jdqr608.duckdns.org/api/v1/",
  timeout: 1000,
  withCredentials: true,
});

const getCookieValue = (name) => {
  const matches = document.cookie.match(
    new RegExp(
      `(?:^|; )${name.replace(/([.*+?^${}()|[\]\\])/g, "\\$1")}=([^;]*)`
    )
  );
  return matches ? decodeURIComponent(matches[1]) : undefined;
};

// tableToken을 확인하고 없으면 extractTableInfo 호출
const initializeToken = async () => {
  const token = localStorage.getItem("tableToken");
  if (token) {
    console.log("로컬 스토리지에서 토큰을 가져왔습니다.");
  } else {
    console.log("토큰이 없어 extractTableInfo를 호출합니다.");
    await extractTableInfo();
  }
};

// tableId와 token을 URL에서 추출하여 저장
const extractTableInfo = async () => {
  try {
    const params = new URLSearchParams(window.location.search);
    const tableId = params.get("tableId");
    const token = params.get("token");
    if (tableId && token) {
      localStorage.setItem("tableId", tableId);
      localStorage.setItem("tableToken", token);
      console.log("tableId와 token이 성공적으로 저장되었습니다.");

      await setUserCookie();
    } else {
      localStorage.setItem("tableId", "6721aa9b0d22a923091eef73");
      localStorage.setItem("tableToken", "dummyTableToken");
      console.log("일단 dummy토큰을 저장했음.");

      await setUserCookie();
    }
  } catch (error) {
    console.log(error);
  }
};

// 쿠키 발급 함수
const setUserCookie = async () => {
  const token = localStorage.getItem("tableToken");

  if (!token) {
    console.error("토큰이 존재하지 않아 쿠키 발급 요청을 할 수 없습니다.");
    return;
  }

  try {
    let userId = sessionStorage.getItem("userId");

    if (!userId) {
      const response = await axiosInstance.get(
        "https://jdqr608.duckdns.org/api/v1/order/cart/cookie"
      );

      userId = getCookieValue("JDQR-order-user-id");
      sessionStorage.setItem("userId", userId);
    }

    // 이후 userId가 sessionStorage에 존재하면 정상적으로 진행됨
    console.log("userId:", userId);
  } catch (error) {
    console.error("쿠키 발급 오류:", error);
    throw new Error("쿠키 발급 중 오류가 발생했습니다.");
  }
};

// 요청 시 Authorization 헤더에 token을 자동으로 추가
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("tableToken");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      console.warn("토큰이 없어 Authorization 헤더에 추가할 수 없습니다.");
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 모듈이 로드될 때 initializeToken 함수를 자동으로 실행
initializeToken();

export default axiosInstance;
export { initializeToken, extractTableInfo, setUserCookie };
