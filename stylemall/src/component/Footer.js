import React from 'react';
import '../css/Footer.css'; // Import your custom CSS

function Footer() {
  return (
    <footer className="footer">
      <div className="container">
        <div className="row">
          <div className="col-md-6">
            <h3 className="logo">Your Logo</h3>
          </div>
          <div className="col-md-3">
            <ul className="links">
              <li><a href="/">Home</a></li>
              <li><a href="/about">About Us</a></li>
              <li><a href="/contact">Contact Us</a></li>
              {/* Add more links here */}
            </ul>
          </div>
          <div className="col-md-3">
            <div className="social-icons">
              <a href="#"><i className="fab fa-facebook fa-lg"></i></a>
              <a href="#"><i className="fab fa-twitter fa-lg"></i></a>
              <a href="#"><i className="fab fa-instagram fa-lg"></i></a>
            </div>
          </div>
        </div>
        <div className="row mt-3">
          <div className="col">
            <p className="text-center">&copy; 2023 Your Company. All rights reserved.</p>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
