import React, { useState, useEffect, useCallback } from 'react';
import { Link } from 'react-router-dom';
import './Hero.css';
import heroImage1 from '../../Assets/image1.jpg';
import heroImage2 from '../../Assets/image2.jpg';
import arrowImage from '../../Assets/arr.png';

function Hero() {
  const [currentImage, setCurrentImage] = useState(heroImage1);
  const [currentText, setCurrentText] = useState({
    heading: 'Meaningful investments in Main Street businesses',
    text: 'Browse vetted investment offerings in communities all over the US.',
    buttonText: 'View Product',
  });

  const handleArrowClick = useCallback(() => {
    if (currentImage === heroImage1) {
      setCurrentImage(heroImage2);
      setCurrentText({
        heading: 'CryptoRevolution: Pioneering the Future of Finance',
        text: 'Dive into the future of finance with our cutting-edge cryptocurrency solutions. Unleash the power of decentralized technology, where innovation meets security.',
        buttonText: 'View Product',
      });
    } else {
      setCurrentImage(heroImage1);
      setCurrentText({
        heading: 'Meaningful investments in Main Street businesses',
        text: 'Browse vetted investment offerings in communities all over the US.',
        buttonText: 'View Product',
      });
    }
  }, [currentImage]);

  useEffect(() => {
    const intervalId = setInterval(() => {
      handleArrowClick();
    }, 5000);

    return () => clearInterval(intervalId);
  }, [currentImage, handleArrowClick]);

  return (
    <div className="hero" style={{ backgroundImage: `url(${currentImage})` }}>
      <img src={arrowImage} alt="Arrow" className="hero-arrow hero-arrow-left" onClick={handleArrowClick} />

      <div className="hero-content">
        <h1 className="hero-heading">{currentText.heading}</h1>
        <p className="hero-text">{currentText.text}</p>
        <Link to="/ProductList" className="hero-button">
          {currentText.buttonText}
        </Link>
      </div>

      <img src={arrowImage} alt="Arrow" className="hero-arrow hero-arrow-right" onClick={handleArrowClick} />
    </div>
  );
}

export default Hero;