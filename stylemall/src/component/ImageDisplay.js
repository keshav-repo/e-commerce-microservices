import React, { useState } from 'react';
import '../css/ImageDisplay.css';

function ImageDisplay({ imageUrls }) {

  //console.log(iu);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  const handleNextImage = () => {
    setCurrentImageIndex((currentImageIndex + 1) % imageUrls.length);
  };

  const handlePrevImage = () => {
    setCurrentImageIndex((currentImageIndex - 1 + imageUrls.length) % imageUrls.length);
  };

  return (
    <div className="image-display-container">
      <div className="image-container">
        <img src={imageUrls[currentImageIndex]} alt="Product" className="main-product-image" />
      </div>
      <div className="thumbnail-container">
        {imageUrls.map((imageUrl, index) => (
          <img
            key={index}
            src={imageUrl}
            alt={`Thumbnail ${index + 1}`}
            className={`thumbnail ${index === currentImageIndex ? 'active' : ''}`}
            onClick={() => setCurrentImageIndex(index)}
          />
        ))}
      </div>
    </div>
  )
}

export default ImageDisplay;

