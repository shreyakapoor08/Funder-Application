import React from 'react';
import './PrivacyPolicy.css';
import Navbar from '../../Common/Navbar';
import Footer from '../../Common/Footer';

const PrivacyPolicy = () => {
  return (
    <body>
    <Navbar />
    <div className="privacy-policy-container">
    <h1 className="privacy-policy-heading">Privacy Policy - Funder</h1>

    <p className="section">
      <strong>Last Updated:</strong> [Nov 13, 2023]
    </p>
    <p className="section">
      <strong>1. Introduction</strong><br />
      Funder we are committed to protecting your privacy. This Privacy Policy explains how your personal information is collected, used, and disclosed by Funder.
    </p>
    <p className="section">
      <strong>3. How We Use Your Information</strong><br />
      We use the collected information for the following purposes:<br /><br />

      <strong>Account Management:</strong><br />
      Managing your account on Funder.<br /><br />

      <strong>Transaction Processing:</strong><br />
      Processing transactions related to your contributions or investments.<br /><br />

      <strong>Communication:</strong><br />
      Sending you important updates, newsletters, and notifications.
    </p>
    <p className="section">
      <strong>4. Information Sharing</strong><br />
      We may share your information in the following circumstances:<br /><br />

      <strong>Service Providers:</strong><br />
      Sharing data with third-party service providers for payment processing and other essential services.<br /><br />

      <strong>Legal Compliance:</strong><br />
      Complying with applicable laws, regulations, or legal processes.
    </p>
    <p className="section">
      <strong>5. Your Choices</strong><br /><br />

      <strong>Account Information:</strong><br />
      You can review and update your account information by logging into your account.<br /><br />

      <strong>Communication Preferences:</strong><br />
      You can opt-out of receiving non-essential communications from us.
    </p>
    <p className="section">
      <strong>6. Security</strong><br />
      We take reasonable measures to protect your personal information from unauthorized access or disclosure.
    </p>

    <p className="section">
      <strong>7. Changes to this Privacy Policy</strong><br />
      We may update this Privacy Policy periodically. We will notify you of any changes by posting the new Privacy Policy on this page.
    </p>
    <p className="section">
      <strong>8. Contact Us</strong><br />
      For any questions or concerns regarding this Privacy Policy, please contact us at <a href="mailto:suport.funder@gmail.com">suport.funder@gmail.com</a>.
    </p>
  </div>
  <Footer />
    </body>
  );
};

export default PrivacyPolicy;