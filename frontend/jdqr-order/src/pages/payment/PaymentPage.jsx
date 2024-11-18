import { Stack, Box, Typography } from "@mui/material";
import Header from "../../components/header/Header";
import PaymentList from "./PaymentList";
import Footer from "../../components/footer/Footer";
import { useQuery } from "@tanstack/react-query";
import { fetchPaymentList } from "../../utils/apis/order";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";
import NoFoodIcon from "@mui/icons-material/NoFood";
import { colors } from "../../constants/colors";
import { useEffect } from "react";
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
  useEffect(() => {
    if (paymentList) {
      console.log("Fetched payment data:", paymentList);
    }
  }, [paymentList]);
  return (
    <Box>
      <Header title="결제하기" BackPage={true} />
      {isLoading && (
        <LoadingSpinner message={"결제 내역을 불러오는 중입니다."} />
      )}
      {!isLoading && !isError && <PaymentList orders={paymentList} />}
      {paymentList && paymentList.orders.dishes?.length === 0 && (
        <Stack sx={{ textAlign: "center", alignItems: "center" }}>
          <NoFoodIcon sx={{ fontSize: 100, color: colors.main.primary500 }} />
          <Typography
            sx={{
              fontweight: 600,
              fontSize: 20,
            }}
          >
            주문 내역이 없습니다.
          </Typography>
        </Stack>
      )}
      <Footer />
    </Box>
  );
};

export default PaymentPage;
