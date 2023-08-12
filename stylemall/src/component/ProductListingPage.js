import React, { useState } from 'react';
import '../css/ProductListingPage.css';
import ProductCard from './ProductCard';
import FilterComponent from './FilterComponent';

function ProductListingPage() {
  const products = [
    {
        id: 1,
        name: 'Product 1',
        image: 'http://0.0.0.0:8080/1.jpg',
        price: 29.99,
    },
    {
        id: 2,
        name: 'Product 2',
        image: 'http://0.0.0.0:8080/10.jpg',
        price: 39.99,
    },
    {
        id: 3,
        name: 'Product 3',
        image: 'http://0.0.0.0:8080/11.jpg',
        price: 39.99,
    },
    {
        id: 4,
        name: 'Product 4',
        image: 'http://0.0.0.0:8080/12.jpg',
        price: 39.99,
    }
  ];

  const categories = [
    { id: 1, name: 'Electronics' },
    { id: 2, name: 'Clothing' },
  ];

  const [selectedCategories, setSelectedCategories] = useState([]);

  const handleCategoryChange = event => {
    const categoryId = parseInt(event.target.value);
    if (event.target.checked) {
      setSelectedCategories([...selectedCategories, categoryId]);
    } else {
      setSelectedCategories(selectedCategories.filter(id => id !== categoryId));
    }
  };

  const filteredProducts = selectedCategories.length > 0
    ? products.filter(product => selectedCategories.includes(product.categoryId))
    : products;


return (
    <div className="product-listing">
      <div className="container">
        <div className="row">
          <div className="col-md-3">
            <FilterComponent
              categories={categories}
              selectedCategories={selectedCategories}
              onChange={handleCategoryChange}
            />
          </div>
          <div className="col-md-9">
            <div className="product-grid">
              {filteredProducts.map(product => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );




}

export default ProductListingPage;
