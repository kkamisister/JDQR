import { BrowserRouter, Routes, Route } from "react-router-dom";
import HomePage from "./pages/place/HomePage";
import DishPage from "./pages/dish/DishPage";
import CartPage from "./pages/cart/CartPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/dish" element={<DishPage />} />
        <Route path="/cart" element={<CartPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
