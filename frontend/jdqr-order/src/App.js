import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "./pages/place/HomePage";
import DishPage from "./pages/dish/DishPage";
import CartPage from "./pages/cart/CartPage";
import PaymentPage from "./pages/payment/PaymentPage";
import { SnackbarProvider } from "notistack";

function App() {
  return (
    <SnackbarProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/dish" element={<DishPage />} />
          <Route path="/cart" element={<CartPage />} />
          <Route path="/payment" element={<PaymentPage />} />
        </Routes>
      </BrowserRouter>
    </SnackbarProvider>
  );
}

export default App;
