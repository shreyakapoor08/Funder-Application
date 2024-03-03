import React from 'react';
import Navbar from '../../Common/Navbar';
import Footer from '../../Common/Footer';
import AboutUs_img from '../../../Assets/about_us_img.jpg';
import './AboutUs.css'; 

const AboutUs = () => {
  return (
    <body>
    <Navbar />
    <div className="about-us-container">
    <section className="about-us-section">
      <div className="section-content">
        <h2 class="text2">About Us</h2>
        <p class="p_text">
          At Funder, we're on a mission to redefine the way people invest and support innovative projects.
          We believe that everyone should have the opportunity to be part of something extraordinary, irrespective of their financial background.
          Funder is not just a crowdfunding platform; it's a community-driven initiative that empowers both funders and investors to shape the future together.
        </p>
      </div>
    </section>

    <section className="mission-vision-section">
    <div className="section-content">
      <h2 class="text2">Our Mission and Vision</h2>
      <div className="image-container">
        <img src={AboutUs_img} alt="Mission and Vision" class="imgabout" />
        <div className="text-on-image">
          <p class="p_text2">
            Our vision is simple: to make investing accessible to all.
            We envision a world where groundbreaking ideas find the support they need to thrive,
            and where individuals, regardless of their financial capacity, can contribute to projects that resonate with them.
          </p>
        </div>
      </div>
    </div>
  </section>

    <section className="meet-the-team-section">
      <div className="section-content">
        <h2 class="text2">Meet the Team</h2>
        <p class="p_text">
          Behind Funder is a diverse team of passionate individuals who share a common goal - to democratize the investment landscape.
          Our team brings together a wealth of experience in finance, technology, and community building.
          We're united by the belief that by working together, we can unlock untapped potential and drive positive change.
        </p>
      </div>
    </section>

    <section className="join-the-movement-section">
      <div className="section-content">
        <h2 class="text2">Join the Movement</h2>
        <p class="p_text">
          Join the Movement
          We invite you to join us on this journey. Become a part of Funder and help shape the future of investment.
          Together, let's break down barriers, support innovation, and create a world where everyone has the chance to make a difference.
          Thank you for being a part of Funder!
        </p>
      </div>
    </section>
  </div>
  <Footer />
  </body>
    
  );
};

export default AboutUs;
