import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Search.css';
import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';

const Search = ({ onSearch }) => {

    const navigate = useNavigate();
    const [searchTerm, setSearchTerm] = useState('');
    const [query, setQuery] = useState('');
    const [sugestionList, setSugestionList] = useState([{value: 'Lehenga'},{ value: 'Kurta'}]);

    const handleSearch = () => {
        if (searchTerm.trim() !== '') {
            navigate(`/products?search=${encodeURIComponent(searchTerm)}`);
        }
    };

    const baseUrl = 'http://localhost:8100';

    useEffect(() => {
        handleSearch();
    }, [searchTerm]);

    useEffect(()=>{
        handleSuggestionSelect();
    }, [query])

    const handleSuggestionSelect = () => {
        if ( query && query.length >= 3) {
            const url = `${baseUrl}/api/search/autosuggest?q=${query}`;
            fetch(url)
                .then(response => response.json())
                .then(data => {
                    setSugestionList(data);
                })
                .catch(error => console.error('Error fetching suggestion list:', error));
        }
    };

    const handleKeyEvent = (e) => {
        if (e.key == 'Enter') {
            e.defaultMuiPrevented = true;
            setSearchTerm(e.target.value);
        }
    };

    const handleInputvalueChange = (e, newVal) => {
        e.defaultMuiPrevented = true;
        if (newVal && newVal.value && newVal.value.length >= 3) {
        }
        else if(newVal.length>=3){
            setQuery(newVal);
        }
    };

    const customEquals = (option, value) => {
        if (option.value === value.value)
            return true;
        else
            return true;
    };
    const optionLabelCustom = (s)=>{
        if(s && s.value){
            return s.value;
        }else{
            return s;
        }
    }

    return (
        <div style={{ display: 'flex', alignItems: 'center' }}>
            <Autocomplete
                freeSolo
                disablePortal
                id="combo-box-demo"
                options={sugestionList}
                getOptionLabel={optionLabelCustom}
                isOptionEqualToValue={customEquals}
                sx={{ width: '300px' }}
                onKeyDown={handleKeyEvent}
                onInputChange={handleInputvalueChange}
                renderInput={(params) =>
                     <TextField 
                        {...params} 
                        label=""
                        inputProps={{
                            ...params.inputProps,
                            style: { height: '10px' }
                        }} 
                     />}
            />
             <button className="btn btn-primary" type="button" onClick={(e) =>{ setSearchTerm(query); } } >
                Search
             </button>
        </div>
    );
};

export default Search;
