import BaseButton from "../../components/button/BaseButton";
import { Stack, Box } from "@mui/material";
import Header from "../../components/header/Header";
import PaymentList from "./PaymentList";
import Footer from "../../components/footer/Footer";
import { useQuery } from "@tanstack/react-query";
import { fetchPaymentList } from "../../utils/apis/order";
import LoadingSpinner from "../../components/Spinner/LoadingSpinner";

const PaymentPage = () => {
  // const mockData = {
  //   status: 200,
  //   message: "주문 조회를 완료하였습니다",
  //   data: {
  //     tableName: "1층 창가 - 1번",
  //     dishCnt: 2,
  //     userCnt: 5,
  //     paymentType: "MENU_DIVIDE",
  //     price: 22800,
  //     restPrice: 10000,
  //     orders: [
  //       {
  //         orderId: 11,
  //         price: 22800,
  //         dishCnt: 2,
  //         dishes: [
  //           {
  //             dishId: 43,
  //             userId: "undefined",
  //             dishName: "브라운슈가 치즈폼 스무디",
  //             dishCategoryId: 16,
  //             dishCategoryName: "스무디",
  //             price: 5700,
  //             totalPrice: 11400,
  //             options: [
  //               {
  //                 optionId: 4,
  //                 optionName: "토핑 선택",
  //                 choiceId: 27,
  //                 choiceName: "알로에",
  //                 price: 5700,
  //               },
  //             ],
  //             quantity: 2,
  //             restQuantity: 1,
  //           },
  //         ],
  //       },
  //     ],
  //   },
  // };

  // const paymentList = mockData.data;
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
  // const isLoading = false;
  // const isError = false;
  return (
    <Box>
      <Header title="결제하기" BackPage={true} />
      {isLoading && (
        <LoadingSpinner message={"결제 내역을 불러오는 중입니다."} />
      )}
      {!isLoading && !isError && <PaymentList orders={paymentList} />}
      {paymentList && paymentList.orders.dishes?.length === 0 && (
        <Box>주문 내역이 없습니다</Box>
      )}
      <Footer />
    </Box>
  );
};

export default PaymentPage;
