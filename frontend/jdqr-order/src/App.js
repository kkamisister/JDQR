import {
  BrowserRouter,
  Routes,
  Route,
  useLocation,
  useNavigate,
} from "react-router-dom";
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage";
import DishPage from "./pages/dish/DishPage";
import CartPage from "./pages/cart/CartPage";
import PaymentPage from "./pages/payment/PaymentPage";
import { SnackbarProvider, useSnackbar } from "notistack";
import HomePage from "./pages/place/main/HomePage";
import DishDetailPage from "./pages/dish/detail/DishDetailPage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import OrderPage from "./pages/order/OrderPage";
import PaymentValidationPage from "./pages/payment/validation/PaymentValidationPage";
import { FailurePage } from "./pages/payment/toss/Failure";
import CelebrationPage from "./pages/payment/validation/CelebrationPage";
import { fetchOrderStatus } from "./utils/apis/order";
import { useEffect } from "react";
import { CheckoutPage } from "./pages/payment/toss/Checkouts";

const queryClient = new QueryClient();

function App() {
  return (
    <SnackbarProvider
      maxSnack={3}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
          {/* <WithRouteProtection> */}
          <Routes>
            <Route path="/place" element={<HomePage />} />
            <Route
              path="/place/:restaurantId"
              element={<RestaurantDetailPage />}
            />

            <Route path="/dish" element={<DishPage />} />
            <Route path="/dish/:dishId" element={<DishDetailPage />} />
            <Route path="/cart" element={<CartPage />} />
            <Route path="/payment" element={<PaymentPage />} />
            <Route path="/toss" element={<CheckoutPage />} />
            <Route path="/order" element={<OrderPage />} />
            <Route path="/success" element={<PaymentValidationPage />} />
            <Route path="/fail" element={<FailurePage />} />
            <Route path="/celebration" element={<CelebrationPage />} />
          </Routes>
          {/* </WithRouteProtection> */}
        </BrowserRouter>
      </QueryClientProvider>
    </SnackbarProvider>
  );
}

// 특정 경로 접근 차단 로직 추가 (PAY_WAITING 상태만 제한)
// function WithRouteProtection({ children }) {
//   const location = useLocation();
//   const navigate = useNavigate();
//   const { enqueueSnackbar } = useSnackbar();

//   useEffect(() => {
//     const checkAndRestrict = async () => {
//       try {
//         const response = await fetchOrderStatus();

//         if (response?.orderStatus === "PAY_WAITING") {
//           const restrictedPaths = ["/dish", "/cart", "/order"];
//           const isRestricted =
//             restrictedPaths.some((path) =>
//               location.pathname.startsWith(path)
//             ) || /^\/dish\/[^/]+$/.test(location.pathname); // /dish/:dishId 처리

//           if (isRestricted) {
//             enqueueSnackbar(
//               "결제가 완료되지 않았습니다. 결제 페이지로 이동합니다.",
//               {
//                 variant: "warning",
//                 autoHideDuration: 3000,
//               }
//             );
//             navigate("/payment", { replace: true }); // /payment로 리다이렉트
//           }
//         }
//       } catch (error) {
//         console.error("주문 상태 조회 중 오류 발생:", error);
//       }
//     };

//     checkAndRestrict();
//   }, [location, enqueueSnackbar, navigate]);

//   return children;
// }

// // fetchOrderStatus를 호출하여 상태 확인 후 처리
// function WithOrderStatusRedirect() {
//   const { enqueueSnackbar } = useSnackbar();
//   const navigate = useNavigate();

//   useEffect(() => {
//     const checkOrderStatus = async () => {
//       try {
//         const response = await fetchOrderStatus();
//       } catch (error) {
//         console.error("주문 상태 조회 중 오류 발생:", error);
//       }
//     };

//     checkOrderStatus();
//   }, [enqueueSnackbar, navigate]);

//   return null;
// }

export default App;
