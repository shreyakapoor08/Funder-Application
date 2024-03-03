import React, { useState } from "react";
import "./ContactUs.css";
import Navbar from "../../Common/Navbar";
import config from "../../../config";

const ContactUs = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    subject: "",
    message: "",
  });
  const URL = config.URL_PATH;
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await fetch(`${URL}/contact/sendEmail`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    })
      .then((data) => {
        setShowSuccessMessage(true);
        setTimeout(() => {
          window.location.href = "/";
        }, 2500);
      })
      .catch((error) => console.error("Error:", error));
  };

  return (
    <body>
      <Navbar />
      <div className="wrapper">
        <div className="box-wrapper">
          <div className="box">
            <h2> Contact Us</h2>
            <form id="form" onSubmit={handleSubmit}>
              <div>
                <input
                  type="text"
                  name="name" // Add name attribute
                  id="name"
                  required
                  placeholder=" "
                  onChange={handleChange} // Add onChange handler
                />
                <label htmlFor="name">Name: </label>
              </div>
              <div>
                <input
                  type="email"
                  name="email" // Add name attribute
                  required
                  placeholder=" "
                  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$"
                  onChange={handleChange} // Add onChange handler
                />
                <label htmlFor="email">Email </label>
              </div>
              <div>
                <input
                  type="text"
                  name="subject" // Add name attribute
                  required
                  placeholder=" "
                  onChange={handleChange} // Add onChange handler
                />
                <label htmlFor="subject">Subject</label>
              </div>
              <div>
                <textarea
                  name="message" // Add name attribute
                  required
                  placeholder=" "
                  onChange={handleChange} // Add onChange handler
                ></textarea>
                <label htmlFor="message">Message</label>
              </div>
              <input id="submit" type="submit" value="Submit" />
            </form>
            {showSuccessMessage && (
              <p className="success-message">
                Form submitted successfully! Redirecting...
              </p>
            )}
          </div>
        </div>
      </div>
    </body>
  );
};

export default ContactUs;
