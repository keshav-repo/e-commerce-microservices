import React, { useEffect, useState } from 'react';
import '../css/ProductDetailPage.css';
import ImageDisplay from './ImageDisplay';
import { useParams } from 'react-router-dom';

function ProductDetailPage() {

  const [product, setProductDetail] = useState({});
  const params = useParams();
  const baseUrl = 'http://localhost:8100';

  useEffect(() => {
    const url = `${baseUrl}/api/product/${params['productId']}`;
    fetch(url)
      .then(response => response.json())
      .then(p => {
        if (p && p.imageList) {
          p.imageList = p.imageList.map(url => {
            const matches = url.match(/"(.*?)"/);
            return matches ? matches[1] : null;
          });
        }
        setProductDetail(p);
      })
  }, [])

  if (!product) {
    return <div>Product not found</div>;
  }

  return (
    <div className="product-detail">
      <div className="container">
        <div className="row">

          {product && product.imageList && (
            <div className="col-md-6">
              <ImageDisplay imageUrls={product.imageList} />
            </div>
          )}

          {product && (
            <div className="col-md-6">
              <h2>{product.name}</h2>
              <p>Category: {product.category}</p>
              <p>Brand: {product.brand}</p>
              <p>Rating: {product.stars}</p>
              <p>Discount: {product.disPrice}</p>
              <p>Actual Price: {product.actualPrice}</p>
              {product.sizes && (
                <p>Size Options: {product.sizes.join(', ')}</p>
              )}

              <button className="buy-button">Add to bag</button>
              
              {product.specs && (
                <div className="product-specs">
                  <h4>Specifications:</h4>
                  <ul>
                    {Object.entries(product.specs).map(([key, value]) => (
                      <li key={key}>
                        <strong>{key}</strong> {value}
                      </li>
                    ))}
                  </ul>
                </div>
              )}
            </div>
          )}

        </div>
      </div>
    </div>
  );

}

export default ProductDetailPage;
