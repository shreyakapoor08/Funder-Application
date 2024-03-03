import ProductList from "./Components/Products/ProductList";
import SignUp from "./Components/SignUp/SignUp";
import Login from "./Components/Login/Login";
import ForgotPassword from "./Components/Forgot Password/ForgotPassword";
import SetNewPassword from "./Components/Set New Password/SetNewPassword";
import HomePage from "./Components/HomePage/HomePage";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Dashboard from "./Components/Dashboard/Dashboard";
import { FluentProvider, teamsLightTheme } from "@fluentui/react-components";
import { ProductDetails } from "./Components/ProductDetails/ProductDetails";
import AboutUs from "./Components/FooterLinks/AboutUs/AboutUs";
import PrivacyPolicy from "./Components/FooterLinks/PrivacyPolicy/PrivacyPolicy";
import Accessibility from "./Components/FooterLinks/Accessibility/Accessibility";
import ContactUs from "./Components/FooterLinks/ContactUs/ContactUs";
import TermsOfUse from "./Components/FooterLinks/TermofUse/TermofUse";
import Blog from "./Components/Blog/Blog";
import BlogDetails from "./Components/BlogDetails/BlogDetails";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/productlist" element=<ProductList /> />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element=<SignUp /> />
          <Route path="/productDetails" element={<ProductDetails />} />
          <Route path="/send-forgot-password-email" element=<ForgotPassword />/>
          <Route path="/reset-password" element=<SetNewPassword /> />
          <Route path="/dashboard/*" element={<FluentProvider theme={teamsLightTheme}><Dashboard /></FluentProvider>} />
          <Route path="/about-us" element=<AboutUs /> />
          <Route path="/privacy-policy" element=<PrivacyPolicy /> />
          <Route path="/accessibility" element=<Accessibility /> />
          <Route path="/terms-of-use" element=<TermsOfUse /> />
          <Route path="/contact" element=<ContactUs /> /> 
          <Route path="/blog" element=<Blog /> />
          <Route path="/blog/getBlog/:id" element=<BlogDetails /> />   
        </Routes>
      </Router>
    </div>
  );
}

export default App;
