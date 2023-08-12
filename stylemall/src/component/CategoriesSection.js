import React from 'react';

function CategoriesSection() {
  const categories = [
    'Electronics',
    'Clothing',
    'Home & Living',
    'Beauty & Personal Care',
  ];

  return (
    <section className="categories-section">
      <div className="container">
        <h2 className="section-title">Shop by Categories</h2>
        <div className="category-list">
          {categories.map((category, index) => (
            <div className="category-card" key={index}>
              <h3>{category}</h3>
              <a href={`/category/${category.toLowerCase()}`} className="btn btn-secondary">
                Explore
              </a>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}

export default CategoriesSection;
