import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Search.css';

const Search = ({ onSearch }) => {

    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState('');
    const [sugestionList, setSugestionList] = useState([]);

    const handleSearch = () => {
        if (searchTerm.trim() !== '') {
            // console.log('handle search');
            // console.log(searchTerm);
            // onSearch(searchTerm);
            navigate(`/products?search=${encodeURIComponent(searchTerm)}`);
        }
    };

    const baseUrl = 'http://localhost:8100';
    useEffect(() => {

        if (searchTerm.length >= 3) {
            const url = `${baseUrl}/api/search/autosuggest?q=${searchTerm}`;
            // fetch(url)
            //     .then(response => response.json())
            //     .then(data => {
            //         setSugestionList(data);
            //     })
            //     .catch(error => console.error('Error fetching suggestion list:', error));
        }

    }, [searchTerm]);

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
            {sugestionList.length > 0 && (
                <ul className="suggestions-list">
                    {sugestionList.map((suggest, index) => (
                        <li key={index} className="suggestion-item" >{suggest.value}</li>
                    ))}
                </ul>
            )}
            <button className="btn btn-primary" type="button" onClick={handleSearch}>Search</button>
        </div>
    );

    // return (
    //     <div className="d-flex align-items-center">
    //         <input
    //             className="form-control me-2"
    //             type="text"
    //             placeholder="Search for products..."
    //             value={searchTerm}
    //             aria-label="Search"
    //             onChange={(e) => setSearchTerm(e.target.value)}
    //         />
    //         {sugestionList.length > 0 && (
    //             <ul className="suggestions-list list-unstyled">
    //                 {sugestionList.map((suggest, index) => (
    //                     <li key={index} className="suggestion-item">{suggest.value}</li>
    //                 ))}
    //             </ul>
    //         )}
    //         <button className="btn btn-primary" type="button" onClick={handleSearch}>
    //             Search
    //         </button>
    //     </div>
    // );

};

export default Search;
