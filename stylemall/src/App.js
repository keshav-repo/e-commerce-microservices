import logo from './logo.svg';

import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './component/HomePage';
import ProductListingPage from './component/ProductListingPage';
import ProductDetailPage from './component/ProductDetailPage';
import Header from './component/Header';
import Footer from './component/Footer';

function App() {
  return (
    <Router>
        <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/products" exact element={<ProductListingPage />} />
        <Route path="/products/:productId" element={<ProductDetailPage />} />
      </Routes>
      <Footer/>
    </Router>
  );
}

export default App;
