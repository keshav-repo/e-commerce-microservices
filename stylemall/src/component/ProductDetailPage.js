// ProductDetailPage.js
import React from 'react';
import { useParams } from 'react-router-dom';

function ProductDetailPage() {
  const { productId } = useParams();

  return (
    <div>
      <h2>Product Detail</h2>
      <p>Product ID: {productId}</p>
    </div>
  );
}

export default ProductDetailPage;
