import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Search = ({ onSearch }) => {

    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearch = () => {
        if (searchTerm.trim() !== '') {
            // console.log('handle search');
            // console.log(searchTerm);
            // onSearch(searchTerm);
            navigate(`/products?search=${encodeURIComponent(searchTerm)}`);
        }
    };

    return (
        <div className="d-flex align-items-center">
            <input
                className="form-control me-2"
                type="text"
                placeholder="Search for products..."
                value={searchTerm}
                aria-label="Search"
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button className="btn btn-primary" type="button" onClick={handleSearch}>Search</button>
        </div>
    );

};

export default Search;
