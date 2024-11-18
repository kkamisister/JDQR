import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const clientKey = "test_ck_d46qopOB89JwBn4D9R7d3ZmM75y0"; // 클라이언트 키 설정
const userId = sessionStorage.getItem("userId") || ""; // 사용자 ID 가져오기
const customerKey = userId.length > 50 ? userId.slice(0, 45) : userId; // 사용자 키 45자 제한

export default function SdkCheckoutPage() {
  const location = useLocation();
  const { orderId, value, orderName } = location.state || {}; // URL state에서 결제 정보 가져오기
  const [ready, setReady] = useState(false);
  const [paymentWidget, setPaymentWidget] = useState(null);

  useEffect(() => {
    // TossPayments 초기화 스크립트 추가
    const script = document.createElement("script");
    script.src = "https://js.tosspayments.com/v1/payment";
    script.async = true;
    script.onload = () => {
      if (window.TossPayments) {
        const widget = window.TossPayments(clientKey); // TossPayments 초기화
        setPaymentWidget(widget);
        setReady(true); // 준비 완료
        console.log("TossPayments 초기화 성공:", widget);
      } else {
        console.error("TossPayments 객체를 로드하지 못했습니다.");
      }
    };
    script.onerror = () => {
      console.error("TossPayments 스크립트 로드 실패");
    };
    document.body.appendChild(script);

    return () => {
      document.body.removeChild(script); // 컴포넌트 언마운트 시 스크립트 제거
    };
  }, []);

  const handlePayment = async () => {
    if (!paymentWidget || !ready) {
      console.error("TossPayments 객체가 준비되지 않았습니다.");
      return;
    }

    try {
      console.log("결제 요청 시작:", { value, orderId, orderName });
      await paymentWidget.requestPayment("카드", {
        amount: value, // 결제 금액
        orderId,
        orderName: orderName, // 주문 이름
        successUrl: `${window.location.origin}/success`, // 결제 성공 시 리다이렉트 URL
        failUrl: `${window.location.origin}/fail`, // 결제 실패 시 리다이렉트 URL
        customerKey, // 사용자 식별 키
      });
    } catch (error) {
      console.error("결제 요청 중 오류:", error);
      alert("결제 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
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
