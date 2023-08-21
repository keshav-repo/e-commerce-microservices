import React, { useState, useEffect } from 'react';
import '../css/ProductListingPage.css';
import ProductCard from './ProductCard';
import FilterComponent from './FilterComponent';
import Pagination from './Pagination';
import { useLocation } from 'react-router-dom';

function ProductListingPage() {

  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(3);
  //const [searchQuery, setSearchQuery] = useState('');
  const [filters, setFilters] = useState([]);
  const productsPerPage = 10;

  const location = useLocation();
  var searchQuery = new URLSearchParams(location.search).get('search');
  if (searchQuery == null) {
    searchQuery = '';
  }

  const baseUrl = 'http://localhost:8100';

  const paginate = pageNumber => {
    console.log("page changed");
    setCurrentPage(pageNumber);
    const url = `${baseUrl}/api/search?q=${searchQuery}&size=${productsPerPage}&page=${pageNumber}`;
    datafetch(url);
  }

  const datafetch = (url) => {
    fetch(url)
      .then(response => response.json())
      .then(data => {
        setProducts(data.list);
        setTotalPages(data.totalPages);
        setFilters(data.filters);
      })
      .catch(error => console.error('Error fetching products:', error));
  }

  useEffect(() => {
    if (searchQuery != null) {
      const url = `${baseUrl}/api/search/search-filters?q=${searchQuery}&size=${productsPerPage}&page=${currentPage}`;
      datafetch(url);
    } else {
      // setProducts([]);
    }
  }, [searchQuery]);

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
              filters={filters}
              selectedCategories={selectedCategories}
              onChange={handleCategoryChange}
            />
          </div>

          <div className="col-md-9">
            <div className="product-grid">
              {products.map(product => (
                <ProductCard key={product.productId} product={product} />
              ))}
            </div>
          </div>

          <Pagination
            paginate={paginate}
            totalPages={totalPages}
          />

        </div>
      </div>
    </div>
  );


}

export default ProductListingPage;
