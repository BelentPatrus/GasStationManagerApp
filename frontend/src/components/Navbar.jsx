import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          <i className="bi bi-shop me-2"></i>
          Sales Tracker
        </Link>
        
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <Link className="nav-link" to="/sales-summary">
                <i className="bi bi-graph-up me-1"></i>
                Sales Summary
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/addCash">
                <i className="bi bi-cash-coin me-1"></i>
                Add Cash
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/uploadExcel">
                <i className="bi bi-file-earmark-excel me-1"></i>
                Upload Excel
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/lottery">
                <i className="bi bi-ticket-perforated me-1"></i>
                Lottery
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/products">
                <i className="bi bi-backpack2 me-1"></i>
                Products
              </Link>
            </li>
          </ul>
          
          {/* User Info & Logout */}
          <div className="d-flex align-items-center">
            <span className="text-white me-3">
              <i className="bi bi-person-circle me-1"></i>
              {user?.username}
            </span>
            <button
              onClick={handleLogout}
              className="btn btn-outline-light btn-sm"
            >
              <i className="bi bi-box-arrow-right me-1"></i>
              Logout
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;