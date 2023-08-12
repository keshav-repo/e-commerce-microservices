// HomePage.js
import React from 'react';

import Header from './Header';
import FeaturedProductsSection from './FeaturedProductsSection';
import BannerCarousel from './BannerCarousel';
import Footer from './Footer';

function HomePage() {
    return (
        <div>
            <Header />
            <BannerCarousel />
            <FeaturedProductsSection />
            <Footer />
        </div>
    );
}

export default HomePage;
