import BaseButton from "../../components/button/BaseButton";
import { Stack, Box } from "@mui/material";
import Header from "../../components/header/Header";
import PaymentList from "./PaymentList";
import Footer from "../../components/footer/Footer";
import { useQuery } from "@tanstack/react-query";
import { fetchPaymentList } from "../../utils/apis/order";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";

const PaymentPage = () => {
  const {
    data: paymentList,
    isLoading,
    isError,
  } = useQuery({
    queryKey: ["paymentList"],
    queryFn: fetchPaymentList,
    refetchOnMount: true,
    refetchOnWindowFocus: true,
    refetchOnReconnect: true,
  });
  return (
    <Box>
      <Header title="결제하기" BackPage={true} />
      {isLoading && (
        <LoadingSpinner message={"결제 내역을 불러오는 중입니다."} />
      )}
      {!isLoading && !isError && <PaymentList orders={paymentList.orders} />}
      {paymentList && paymentList.orders.dishes.length === 0 && (
        <Box>주문 내역이 없습니다</Box>
      )}
      <Footer />
    </Box>
  );
};

export default PaymentPage;
