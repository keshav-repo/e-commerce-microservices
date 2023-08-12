import React from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

function BannerCarousel() {
  const banners = [
    'http://0.0.0.0:8080/1.jpg',
    'http://0.0.0.0:8080/11.jpg',
    'http://0.0.0.0:8080/12.jpg',
    // Add more banner image URLs
  ];

  const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    autoplay: true,
    autoplaySpeed: 5000, // Time between slide changes in milliseconds
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  return (
    <div className="banner-carousel">
      <Slider {...sliderSettings}>
        {banners.map((banner, index) => (
          <div key={index}>
            <img src={banner} alt={`Banner ${index}`} />
          </div>
        ))}
      </Slider>
    </div>
  );
}

export default BannerCarousel;
