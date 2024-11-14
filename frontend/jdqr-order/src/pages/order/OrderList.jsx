import { Stack, Box } from "@mui/material";
import OrderListItem from "./OrderListItem";
import { fetchOrderList } from "../../utils/apis/order";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";
import { useQuery } from "@tanstack/react-query";
import { colors } from "../../constants/colors";
import BaseButton from "../../components/button/BaseButton";

export default function OrderList() {
  const { data, isLoading, isError } = useQuery({
    queryKey: ["orderList"],
    queryFn: fetchOrderList,
  });
  const goToPayment = () => {};
  return (
    <Stack spacing={3} bgcolor={colors.background.box}>
      {isLoading && <LoadingSpinner message={"주문 내역 가져오는 중입니다."} />}
      {!isLoading && !isError && (
        <Box>
          {data.orders?.map((order, index) => (
            <OrderListItem key={order.orderId} order={order} index={index} />
          ))}
          <BaseButton onClick={goToPayment}>결제하기</BaseButton>
        </Box>
      )}
    </Stack>
  );
}
