import { BrowserRouter, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/Index/IndexPage";
import DishPage from "./pages/dish/DishPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<IndexPage />} />
        <Route path="/dish" element={<DishPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
