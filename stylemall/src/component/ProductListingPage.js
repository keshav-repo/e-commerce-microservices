import React, { useState, useEffect } from 'react';
import '../css/ProductListingPage.css';
import ProductCard from './ProductCard';
import FilterComponent from './FilterComponent';
import Pagination from './Pagination';

function ProductListingPage() {

  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(3);
  const productsPerPage = 10;

  const baseUrl = 'http://localhost:8100';

  const paginate = pageNumber => {
    console.log("page changed");
    setCurrentPage(pageNumber);
    const url = `${baseUrl}/api/search?q=kurta&size=${productsPerPage}&page=${pageNumber}`;
    datafetch(url);
  }

  const datafetch = (url) => {
    fetch(url)
      .then(response => response.json())
      .then(data => { setProducts(data.list); setTotalPages(data.totalPages) })
      .catch(error => console.error('Error fetching products:', error));
  }

  useEffect(() => {
    const url = `${baseUrl}/api/search?q=kurta&size=${productsPerPage}&page=${currentPage}`;
    datafetch(url);
  }, []);

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
              {products.map(product => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>
          </div>

          <Pagination
            productsPerPage={productsPerPage}
            totalProducts={products.length}
            currentPage={currentPage}
            paginate={paginate}
            totalPages={totalPages}
          />

        </div>
      </div>
    </div>
  );


}

export default ProductListingPage;
