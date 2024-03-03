import React, { useState, useEffect } from "react";
import { useNavigate, NavLink } from "react-router-dom";
import "./Navbar.css";
import logoImage from "../../Assets/logo.png";

function Navbar() {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [firstName, setFirstName] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  const getFirstNameFromLocalStorage = () => {
    const storedFirstName = localStorage.getItem("firstName");
    if (storedFirstName) {
      setFirstName(storedFirstName);
      setIsLoggedIn(true);
    }
  };

  useEffect(() => {
    getFirstNameFromLocalStorage();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("firstName");
    localStorage.removeItem("userName");
    localStorage.removeItem("id");
    localStorage.removeItem("token");

    setFirstName("");
    setIsLoggedIn(false);
    navigate("/");
  };

  const activeClass = "active";

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <NavLink to="/" className="logo-link" activeClassName="active">
          <img src={logoImage} alt="Company Logo" className="nav_logo" />
        </NavLink>
        <ul className="menu">
          <li>
            <NavLink to="/productlist" activeClassName={activeClass}>
              Product
            </NavLink>
          </li>
          <li>
            <NavLink to="/about-us" activeClassName={activeClass}>
              About&nbsp;Us
            </NavLink>
          </li>
          {isLoggedIn && (
            <li>
              <NavLink to="/dashboard/investment" activeClassName={activeClass}>
                Dashboard
              </NavLink>
            </li>
          )}
        </ul>
      </div>
      <div className="navbar-right">
        {isLoggedIn ? (
          <ul className="menu">
            <li className={`dropdown ${isDropdownOpen ? "open" : ""}`}>
              <a
                href="#Hello User"
                onClick={() => setIsDropdownOpen(!isDropdownOpen)}
              >
                Hello {firstName}
              </a>
              <div className="dropdown-content horizontal">
                <a href="/" onClick={handleLogout}>
                  Logout
                </a>
              </div>
            </li>
          </ul>
        ) : (
          <>
            <NavLink to="/login" activeClassName={activeClass}>
              Login
            </NavLink>
            <NavLink to="/signup" activeClassName={activeClass}>
              Sign Up
            </NavLink>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;