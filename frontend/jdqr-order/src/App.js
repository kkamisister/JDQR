import { BrowserRouter, Routes, Route } from "react-router-dom"
import HomePage from "./pages/place/HomePage"
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage"
import DishPage from "./pages/dish/DishPage"
import CartPage from "./pages/cart/CartPage"
import PaymentPage from "./pages/payment/PaymentPage"
import { SnackbarProvider } from "notistack"

function App() {
  return (
    <SnackbarProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route
            path="/restaurant/detail/:restaurantId"
            element={<RestaurantDetailPage />}
          />
          <Route path="/dish" element={<DishPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/payment" element={<PaymentPage />} />
        </Routes>
      </BrowserRouter>
    </SnackbarProvider>
  )
}

export default App
