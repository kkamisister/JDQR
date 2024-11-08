<<<<<<< HEAD
=======
<<<<<<< HEAD
import { BrowserRouter, Routes, Route } from "react-router-dom"
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage"
import DishPage from "./pages/dish/DishPage"
import CartPage from "./pages/cart/CartPage"
import PaymentPage from "./pages/payment/PaymentPage"
import { SnackbarProvider } from "notistack"
import HomePage from "./pages/place/main/HomePage"
=======
>>>>>>> fe/fix/api파일복구
import { BrowserRouter, Routes, Route } from "react-router-dom";
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage";
import DishPage from "./pages/dish/DishPage";
import CartPage from "./pages/cart/CartPage";
import PaymentPage from "./pages/payment/PaymentPage";
import { SnackbarProvider } from "notistack";
import HomePage from "./pages/place/main/HomePage";
import DishDetailPage from "./pages/dish/detail/DishDetailPage";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClient = new QueryClient();
<<<<<<< HEAD
=======
>>>>>>> cfbfb95 (feat: query client provider 설정)
>>>>>>> fe/fix/api파일복구

function App() {
  return (
    <SnackbarProvider>
<<<<<<< HEAD
=======
<<<<<<< HEAD
      <BrowserRouter>
        <Routes>
          <Route path="/place" element={<HomePage />} />
          <Route
            path="/restaurant/detail/:restaurantId"
            element={<RestaurantDetailPage />}
          />
          <Route path="/dish" element={<DishPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/payment" element={<PaymentPage />} />
        </Routes>
      </BrowserRouter>
=======
>>>>>>> fe/fix/api파일복구
      <QueryClientProvider client={queryClient}>
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
      </QueryClientProvider>
<<<<<<< HEAD
=======
>>>>>>> cfbfb95 (feat: query client provider 설정)
>>>>>>> fe/fix/api파일복구
    </SnackbarProvider>
  );
}

export default App;
