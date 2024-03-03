import React, { useEffect, useState } from 'react';
import './Footer.css';
import FooterImage from '../../Assets/logo2.png';
import { Link } from 'react-router-dom';

function Footer() {

  const [isuserLoggedIn,setIsUserLoggedIn] = useState(false);

  useEffect(()=>{
     isLoggedIn();
  },[])

  function isLoggedIn(){
    const storedFirstName = localStorage.getItem("firstName");
    if (storedFirstName) {
      setIsUserLoggedIn(true);
    }
  }

  return (
    <footer className="footer">
      <div className='t1'>
        <div className="footer-logo">
        <Link to="/"><img src={FooterImage} alt="Footer Logo" /></Link>
        </div>
        <div className="footer-section">
          <p className='footer_title'>ABOUT</p>
          <ul>
            <li>
              <Link to="/about-us">About Us</Link>
            </li>
            <li>
              <Link to="/contact">Contact Us</Link>
            </li>
          </ul>
        </div>
        <div className="footer-section">
          <p className='footer_title'>FEATURES</p>
          <ul>
            <li>
              <Link to="/productlist">Invest</Link>
            </li>
            {isuserLoggedIn ? [
             <> 
              <li>
              <Link to="/dashboard/founder">Founder Dashboard</Link>
            </li>
            <li>
              <Link to="/dashboard/investment">Investment Dashboard</Link>
            </li>
            </>
            ] : [
            <>
              <li>
              <Link to="/login">Founder Dashboard</Link>
            </li>
            <li>
              <Link to="/login">Investment Dashboard</Link>
            </li>
            </>
            ]}
            
          </ul>
        </div>
        <div className="footer-section">
          <p className='footer_title'>STAY UPDATED</p>
          <ul>
            <li>
              <Link to="/blog">Blogs</Link>
            </li>
          </ul>
        </div>
      </div>
      <div className='t2'>
        <div className="footer-line">
          <Link to="/terms-of-use">Terms of Use</Link>
          <Link to="/privacy-policy">Privacy Policy</Link>
          <Link to="/accessibility">Accessibility</Link>
        </div>
        <div className="copyright">
          © 2023 Funder.com All Rights Reserved
        </div>
      </div>
    </footer>
  );
}

export default Footer;
