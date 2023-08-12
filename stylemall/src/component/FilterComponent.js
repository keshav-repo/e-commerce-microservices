import React from 'react';
import './../css/FilterComponent.css';

function FilterComponent({ categories, selectedCategories, onChange }) {
    return (
        <div className="filter-component">
            <h3 className="filter-title">Filter by Categories</h3>
            {categories.map(category => (
                <label className="filter-label" key={category.id}>
                    <input
                        type="checkbox"
                        value={category.id}
                        checked={selectedCategories.includes(category.id)}
                        onChange={onChange}
                    />
                    {category.name}
                </label>
            ))}
        </div>
    );
}

export default FilterComponent;
