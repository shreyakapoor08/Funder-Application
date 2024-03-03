import React from 'react'
import Navbar from '../Common/Navbar';
import Hero from '../Common/Hero';
import RecommendedSection from '../RecommendedSection/RecommendedSection';
import Testimonial from '../Testimonial/Testimonial';
import Footer from '../Common/Footer';

const HomePage = () => {

  return (
    <body>
    <Navbar />
    <Hero />
    <RecommendedSection /> 
    <Testimonial />
    <Footer />
    </body>
    
    
  )
}

export default HomePage