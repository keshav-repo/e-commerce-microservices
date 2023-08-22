import React from 'react';
import './../css/FilterComponent.css';

function FilterComponent({ filters, onCheckboxChange }) {

    const handleCheckboxChange = (filterName, value) => {
        onCheckboxChange(filterName, value);
    };

    return (
        <div className="filter-component">
            {filters.map((filter, idx) => (
                <div key={idx}>
                    <h3 className="filter-title">{filter['name'].toUpperCase()}</h3>
                    {filter.values.slice(0, 10).map((value, id) => (
                        <label className="filter-label" key={id}>
                            <input
                                 type="checkbox" value={value}
                                 onChange={() => handleCheckboxChange(filter.name, value)}
                                />
                            <span className="checkbox-text">{value}</span>
                        </label>
                    ))}
                </div>
            ))}
        </div>
    )
}

export default FilterComponent;
