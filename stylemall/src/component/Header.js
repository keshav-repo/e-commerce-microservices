import React from 'react';

function Header() {
  return (
    <header className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <a className="navbar-brand" href="/">Your Logo</a>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <a className="nav-link" href="/">Mobile</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/">Best Seller</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/">Cloths</a>
            </li>
          </ul>
        </div>
        <div className="d-flex align-items-center">
          <input className="form-control me-2" type="search" placeholder="Search..." aria-label="Search" />
          <button className="btn btn-primary" type="button">Login</button>
        </div>
      </div>
    </header>
  );
}

export default Header;

