import React from 'react';
import './Accessibility.css';
import Navbar from '../../Common/Navbar';
import Footer from '../../Common/Footer';

const Accessibility = () => {
  return (
    <body>
    <Navbar />
    <div className="accessibility-container">
      <h1 className="accessibility-heading">Accessibility Notice</h1>
      <div className="accessibility-content">
        <p>
          <strong>Accessibility</strong>
        </p>
        <p>
          In the spirit of community, Funder is continually working to ensure that members of our community, those that may potentially join our community, and those that are just curious all have access to and can navigate the Funder site.
        </p>
        <p>
          If you are having difficulty viewing the content or navigating the site, please email our team at <a href="mailto:suport.funder@gmail.com">suport.funder@gmail.com</a>. We will be happy to assist you.
        </p>
      </div>
    </div>
    <Footer />
    </body>
  );
};

export default Accessibility;
