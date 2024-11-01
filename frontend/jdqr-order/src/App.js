import { BrowserRouter, Routes, Route } from "react-router-dom"
import HomePage from "./pages/place/HomePage"
import DishPage from "./pages/dish/DishPage"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/dish" element={<DishPage />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
