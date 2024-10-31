import { BrowserRouter, Routes, Route } from "react-router-dom"
import IndexPage from "./pages/Index/IndexPage"
import HomePage from "./pages/place/HomePage"
import Layout from "./Layout"
import DishPage from "./pages/dish/DishPage"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <Layout>
              <HomePage />
            </Layout>
          }
        />
        <Route
          path="/dish"
          element={
            <Layout>
              <DishPage />
            </Layout>
          }
        />
      </Routes>
    </BrowserRouter>
  )
}

export default App
