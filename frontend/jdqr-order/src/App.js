import { BrowserRouter, Routes, Route } from "react-router-dom";
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage";
import DishPage from "./pages/dish/DishPage";
import CartPage from "./pages/cart/CartPage";
import PaymentPage from "./pages/payment/PaymentPage";
import { SnackbarProvider } from "notistack";
import HomePage from "./pages/place/main/HomePage";
import DishDetailPage from "./pages/dish/detail/DishDetailPage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import OrderPage from "./pages/order/OrderPage";
import { CheckoutPage } from "./pages/payment/toss/Checkouts";
import PaymentValidationPage from "./pages/payment/validation/PaymentValidationPage";
import { FailurePage } from "./pages/payment/toss/Failure";

const queryClient = new QueryClient();

function App() {
  return (
    <SnackbarProvider>
      <QueryClientProvider client={queryClient}>
        <BrowserRouter>
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
            <Route path="/order" element={<OrderPage />} />
            <Route path="/toss" element={<CheckoutPage />} />
            <Route path="/success" element={<PaymentValidationPage />} />
            <Route path="/fail" element={<FailurePage />} />
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </SnackbarProvider>
  );
}

export default App;
