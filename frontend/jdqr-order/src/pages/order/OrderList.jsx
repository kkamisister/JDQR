import { Stack, Typography, Divider } from "@mui/material";
import OrderListItem from "./OrderListItem";
import { fetchOrderList } from "../../utils/apis/order";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";
import { useQuery } from "@tanstack/react-query";

export default function OrderList() {
  const { data, isLoading, isError } = useQuery({
    queryKey: ["orderList"],
    queryFn: fetchOrderList,
  });

  return (
    <Stack>
      {isLoading && <LoadingSpinner message={"주문 내역 가져오는 중입니다."} />}
      {!isLoading && !isError && <OrderListItem />}
    </Stack>
  );
}
