import React from 'react';
import '../css/ProductCard.css';
import { Link } from 'react-router-dom';

function ProductCard({ product }) {
  var imageUrls;
  if(product && product.imageList){
    imageUrls = product.imageList.map(url => {
      const matches = url.match(/"(.*?)"/);
      return matches ? matches[1] : null; 
    });
  }
  

  return (
    <div className="col-md-3 product-card">
      <Link to={`/products/${product.id}`}>
        <div className="card">
          <img src={imageUrls ? imageUrls[0]: ''} alt={product.name} className="card-img-top product-image" />
          <div className="card-body">
            <h5 className="card-title product-name">{product.name}</h5>
            <p className="card-text product-price">${product.actualPrice}</p>
            <button className="btn btn-primary">Add to Cart</button>
          </div>
        </div>
      </Link>
    </div>
  );
}

export default ProductCard;
