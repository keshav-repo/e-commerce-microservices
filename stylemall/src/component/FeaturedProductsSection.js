import React from 'react';

function FeaturedProductsSection() {
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

    return (
        <section className="featured-products">
          <div className="container">
            <h2 className="section-title">Featured Products</h2>
            <div className="product-list">
              {products.map(product => (
                <div className="product-card" key={product.id}>
                  <img src={product.image} alt={product.name} />
                  <h3>{product.name}</h3>
                  <p>${product.price.toFixed(2)}</p>
                  <button className="btn btn-primary">Add to Cart</button>
                </div>
              ))}
            </div>
          </div>
        </section>
      );
}

export default FeaturedProductsSection;
