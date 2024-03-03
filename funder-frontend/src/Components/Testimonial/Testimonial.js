import React, { useState, useEffect } from 'react';
import testimonialsData from './Testimonial.json';
import './Testimonial.css';

const Testimonial = () => {
  const [activeIndex, setActiveIndex] = useState(0);

  const nextSlide = () => {
    setActiveIndex((activeIndex + 1) % testimonialsData.length);
  };

  const prevSlide = () => {
    setActiveIndex((activeIndex - 1 + testimonialsData.length) % testimonialsData.length);
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      nextSlide();
    }, 3000);

    return () => {
      clearInterval(intervalId);
    };
  }, [activeIndex]);

  const displayedTestimonials = [
    testimonialsData[(activeIndex + testimonialsData.length - 1) % testimonialsData.length],
    testimonialsData[activeIndex],
    testimonialsData[(activeIndex + 1) % testimonialsData.length],
  ];

  return (
    <div className="testimonial-container">
      <p className='heading_text'>Testimonials</p>
      <div className="slider">
        {displayedTestimonials.map((testimonial, index) => (
          <div key={index} className="testimonial">
            <img src={testimonial.image} alt={testimonial.name} />
            <h3>{testimonial.name}</h3>
            <p className='content_text'>{testimonial.feedback}</p>
            <div className="ratings">
              {Array.from({ length: testimonial.stars }, (_, i) => (
                <span key={i} className="star">&#9733;</span>
              ))}
            </div>
          </div>
        ))}
      </div>
      <div className="controls">
        <button className="btn1" onClick={prevSlide}>&lt;</button>
        <button className="btn1" onClick={nextSlide}>&gt;</button>
      </div>
    </div>
  );
};

export default Testimonial;