import { Stack, Box } from "@mui/material";
import OrderListItem from "./OrderListItem";
import { fetchOrderList } from "../../utils/apis/order";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";
import { useQuery } from "@tanstack/react-query";
import { colors } from "../../constants/colors";
import BaseButton from "../../components/button/BaseButton";
import { useNavigate } from "react-router-dom";
import Footer from "../../components/footer/Footer";

export default function OrderList() {
  const navigate = useNavigate();
  const { data, isLoading, isError } = useQuery({
    queryKey: ["orderList"],
    queryFn: fetchOrderList,
  });
  const goToPayment = () => {
    navigate("/payment");
  };

  return (
    <>
      {isLoading && <LoadingSpinner message={"주문 내역 가져오는 중입니다."} />}
      {!isLoading && !isError && (
        <Box>
          {data.orders?.map((order, index) => (
            <OrderListItem key={order.orderId} order={order} index={index} />
          ))}
          <BaseButton count={data.dishCnt} onClick={goToPayment}>
            총 {data.price.toLocaleString()}원 결제하기
          </BaseButton>
        </Box>
      )}
    </>
  );
}
