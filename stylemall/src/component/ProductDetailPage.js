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

        </div>
      </div>
    </div>
  );

}

export default ProductDetailPage;
