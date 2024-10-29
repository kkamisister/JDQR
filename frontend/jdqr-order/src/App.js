import { BrowserRouter, Routes, Route } from "react-router-dom";
import IndexPage from "./pages/Index/IndexPage";
import Layout from "./Layout";
import DishPage from "./pages/dish/DishPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <Layout>
              <IndexPage />
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
  );
}

export default App;
