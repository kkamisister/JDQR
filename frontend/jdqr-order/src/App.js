import { BrowserRouter, Routes, Route } from "react-router-dom"
import RestaurantDetailPage from "./pages/place/detail/RestaurantDetailPage"
import DishPage from "./pages/dish/DishPage"
import CartPage from "./pages/cart/CartPage"
import PaymentPage from "./pages/payment/PaymentPage"
import { SnackbarProvider } from "notistack"
import HomePage from "./pages/place/main/HomePage"
import DishDetailPage from "./pages/dish/detail/DishDetailPage"
import { QueryClient, QueryClientProvider } from "@tanstack/react-query"

const queryClient = new QueryClient()

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
          </Routes>
        </BrowserRouter>
      </QueryClientProvider>
    </SnackbarProvider>
  )
}

export default App
