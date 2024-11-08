import { BrowserRouter, Routes, Route } from "react-router-dom";
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage";
import DishPage from "./pages/dish/DishPage";
import CartPage from "./pages/cart/CartPage";
import PaymentPage from "./pages/payment/PaymentPage";
import { SnackbarProvider } from "notistack";
import HomePage from "./pages/place/main/HomePage";
import DishDetailPage from "./pages/dish/detail/DishDetailPage";

function App() {
  return (
    <SnackbarProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/place" element={<HomePage />} />
          <Route
            path="/restaurant/detail/:restaurantId"
            element={<RestaurantDetailPage />}
          />
          <Route path="/dish" element={<DishPage />} />
          <Route path="/dish/:dishId" element={<DishDetailPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/payment" element={<PaymentPage />} />
        </Routes>
      </BrowserRouter>
    </SnackbarProvider>
  );
}

export default App;
