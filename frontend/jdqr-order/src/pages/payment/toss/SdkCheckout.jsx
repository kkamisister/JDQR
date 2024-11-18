import { loadTossPayments } from "@tosspayments/tosspayments-sdk";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const clientKey = "test_ck_pP2YxJ4K87BAoKJbayO0rRGZwXLO";
// const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm"; // 클라이언트 키 설정
const customerKey = sessionStorage.getItem("userId"); // 사용자 식별 키 설정

export function SdkCheckoutPage() {
  const location = useLocation();
  const { orderId, value, orderName } = location.state || {};
  const [ready, setReady] = useState(false);
  const [tossPayments, setTossPayments] = useState(null);

  useEffect(() => {
    async function initializeTossPayments() {
      try {
        const payments = await loadTossPayments(clientKey); // SDK 초기화
        setTossPayments(payments);
        setReady(true); // 준비 완료
      } catch (error) {
        console.error("TossPayments SDK 초기화 실패:", error);
      }
    }
    initializeTossPayments();
  }, []);

  const handlePayment = async () => {
    if (!tossPayments || !ready) return;

    try {
      await tossPayments.requestPayment("카드", {
        amount: value,
        orderId: orderId,
        orderName: orderName,
        successUrl: window.location.origin + "/success",
        failUrl: window.location.origin + "/fail",
        customerKey, // 사용자 식별 키
      });
    } catch (error) {
      console.error("결제 요청 중 오류:", error);
      // 결제 실패 처리
    }
  };

  return (
    <div className="wrapper">
      <div className="box_section">
        <h2>결제 진행</h2>
        <p>결제 금액: {value?.toLocaleString()}원</p>
        <button
          className="button"
          style={{ marginTop: "30px" }}
          disabled={!ready}
          onClick={handlePayment}
        >
          결제하기
        </button>
      </div>
    </div>
  );
}

export default SdkCheckoutPage;
