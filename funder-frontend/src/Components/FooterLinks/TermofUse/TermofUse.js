import React from 'react';
import './TermofUse.css';
import Navbar from '../../Common/Navbar';

const TermsOfUse = () => {
  return (
    <div>
    <Navbar />
    <div className="terms-of-use">
      <h2 className="section-heading">Terms of Use Agreement</h2>
      <p className="last-updated">Last Updated: [November 18, 2023]</p>

      <section className="terms-section">
        <h3 className="section-subheading">1. Acceptance of Terms</h3>
        <p>
          Welcome to Funder! By accessing or using our website (the "Site") and our services (the "Services"), you agree to comply with and be bound by the following Terms of Use Agreement ("Agreement"). If you do not agree with these terms, please do not use our Site or Services.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">2. Description of Services</h3>
        <p>
          Funder is a crowdfunding platform that allows users to create and contribute to fundraising campaigns. Users can make financial contributions to support projects, and project creators can offer rewards in return.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">3. User Accounts</h3>
        <p>
          To use certain features of the Services, you may be required to create a user account. You are responsible for maintaining the confidentiality of your account information and for all activities that occur under your account.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">4. Campaign Creation</h3>
        <p>
          Users can create fundraising campaigns on Funder. By creating a campaign, you agree to provide accurate and truthful information. Funder reserves the right to remove or suspend campaigns that violate our policies.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">5. Contributions and Payments</h3>
        <p>
          Users can make financial contributions to campaigns. All payments are processed through secure third-party payment processors. Funder is not responsible for any issues related to payment processing.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">6. Rewards</h3>
        <p>
          Campaign creators may offer rewards to contributors. Funder does not guarantee the fulfillment of rewards and is not responsible for the quality or delivery of rewards.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">7. Prohibited Content</h3>
        <p>
          Users are prohibited from creating campaigns that involve illegal activities, hate speech, or any content that violates Funder's community guidelines.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">8. Termination</h3>
        <p>
          Funder reserves the right to terminate or suspend your account and access to the Services at any time for violation of these terms or for any other reason.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">9. Changes to Terms</h3>
        <p>
          Funder may update these terms at any time. Users will be notified of changes, and continued use of the Services after the changes constitute acceptance of the new terms.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">10. Disclaimer</h3>
        <p>
          Funder makes no warranties or representations about the accuracy or completeness of the content on the Site or Services. Use the Site and Services at your own risk.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">11. Limitation of Liability</h3>
        <p>
          Funder is not liable for any indirect, incidental, consequential, or punitive damages arising out of your use of the Site or Services.
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">12. Governing Law</h3>
        <p>
          This Agreement is governed by and construed in accordance with the laws of [Your Jurisdiction]. Any disputes arising from or in connection with this Agreement shall be subject to the exclusive jurisdiction of the courts in [Your Jurisdiction].
        </p>
      </section>

      <section className="terms-section">
        <h3 className="section-subheading">13. Contact Information</h3>
        <p>If you have any questions or concerns about these Terms of Use, please contact us at <a className="contact-link" href="mailto:suport.funder@gmail.com">suport.funder@gmail.com</a>.</p>
      </section>

      <p className="closing-message">Thank you for using Funder!</p>
    </div>
    </div>
  );
};

export default TermsOfUse;