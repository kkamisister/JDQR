import { BrowserRouter, Routes, Route } from "react-router-dom";
import MenuPage from "./pages/menu/MenuPage";
import IndexPage from "./pages/Index/IndexPage";
import Layout from "./Layout";

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
          path="/menu"
          element={
            <Layout>
              <MenuPage />
            </Layout>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
