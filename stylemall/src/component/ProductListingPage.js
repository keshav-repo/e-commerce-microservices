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
  const [searchQuery, setSearchQuery] = useState('');
  const [filters, setFilters] = useState([]);
  const [selectedFilters, setSelectedFilters] = useState({});
  const productsPerPage = 10;

  const location = useLocation();

  const baseUrl = 'http://localhost:8100';

  const getInitialUrl = () => {
    var q = new URLSearchParams(location.search).get('search');
    q = q == null ? '' : q
    return `${baseUrl}/api/search/search-filters?q=${q}&size=${productsPerPage}&page=${currentPage}`;
  }

  const [url, setUrl] = useState(getInitialUrl());

  const paginate = pageNumber => {
    setCurrentPage(pageNumber);
    const url = `${baseUrl}/api/search?q=${searchQuery}&size=${productsPerPage}&page=${pageNumber}`;
    setUrl(url);
  }

  useEffect(() => {
    fetch(url)
      .then(response => response.json())
      .then(data => {
        setProducts(data.list);
        setTotalPages(data.totalPages);
        if (data.filters) {
          setFilters(data.filters);
        }
      })
      .catch(error => console.error('Error fetching products:', error));
  }, [url]);

  useEffect(() => {
    const q = new URLSearchParams(location.search).get('search');
    const searchQueryValue = q || '';
    setSearchQuery(searchQueryValue);
    const url = `${baseUrl}/api/search/search-filters?q=${searchQueryValue}&size=${productsPerPage}&page=${currentPage}`;
    setUrl(url);
  }, [location.search]);

  const handleCheckboxChange = (filterName, value, isChecked) => {
    var filters = selectedFilters;
    if (isChecked) {
      if (filters[filterName] == null) {
        filters[filterName] = [value];
      } else {
        filters[filterName].push(value);
      }
    } else {
      filters[filterName] = filters[filterName].filter(item => item != value);
    }

    setSelectedFilters(filters);

    var url = `${baseUrl}/api/search?q=${searchQuery}&size=${productsPerPage}&page=${currentPage}`;
    if (filters['brand']) {
      url = url + `&brands=${encodeURIComponent(filters['brand'].join(';'))}`;
    }
    if (filters['category']) {
      url = url + `&categories=${encodeURIComponent(filters['category'].join(';'))}`;
    }
    setUrl(url);
  };

  return (
    <div className="product-listing">
      <div className="container">
        <div className="row">
          <div className="col-md-3">
            <FilterComponent
              filters={filters}
              onCheckboxChange={handleCheckboxChange}
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
