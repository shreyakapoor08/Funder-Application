import React, { useState } from "react";
import axios from "axios";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import logo from "../../Assets/funder-Logo.png";
import { Link, useNavigate } from "react-router-dom";
import CircularProgress from "@mui/material/CircularProgress";
import MuiAlert from "@mui/material/Alert";
import Snackbar from "@mui/material/Snackbar";
import config from '../../config'

export default function SignUp() {
  const [formData, setFormData] = React.useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const URL = config.URL_PATH;
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = React.useState(false);
  const navigate = useNavigate();
  const [showSuccessMessage, setShowSuccessMessage] = React.useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    });

    setErrors({
      ...errors,
      [name]: "",
    });
  };

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setShowSuccessMessage(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const validationErrors = {};
    if (!formData.firstName) {
      validationErrors.firstName = "First Name is required";
    }
    if (!formData.lastName) {
      validationErrors.lastName = "Last Name is required";
    }
    if (!formData.email) {
      validationErrors.email = "Email is required";
    } else if (!/^\S+@\S+\.\S+$/.test(formData.email)) {
      validationErrors.email = "Invalid email address";
    }
    if (!formData.password) {
      validationErrors.password = "Password is required";
    } else if (
      !/^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/.test(
        formData.password
      )
    ) {
      validationErrors.password =
        "Password must contain at least 1 capital letter, 1 numerical digit, and 1 special character (!@#$%^&*)";
    }
    if (formData.password !== formData.confirmPassword) {
      validationErrors.confirmPassword = "Passwords do not match";
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      try {
        setLoading(true);
        const response = await axios.post(
          `${URL}/user/signup`,
          {
            firstName: formData.firstName,
            lastName: formData.lastName,
            userName: formData.email,
            password: formData.password,
          }
        );

        setShowSuccessMessage(true);
        setTimeout(() => {
          navigate("/login");
        }, 2500);
      } catch (error) {
        console.error("API Error:", error);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <Grid container component="main" sx={{ height: "100vh" }}>
      <CssBaseline />
      <Grid
        item
        xs={false}
        sm={4}
        md={7}
        sx={{
          backgroundColor: "#050A24",
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundPosition: "center",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Typography
          variant="h4"
          component="h2"
          sx={{
            color: "#fff",
            textAlign: "center",
            marginBottom: "16px",
          }}
        >
          Make Businesses Bigger With Us!
        </Typography>
      </Grid>

      <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
        <Box
          sx={{
            my: 3,
            mx: 4,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <img src={logo} alt="Logo" width="200" height="60" />
          <Typography
            component="h1"
            variant="h5"
            mt={2}
            mb={2}
            style={{ fontStyle: "italic" }}
          >
            Create An Account
          </Typography>
          <form onSubmit={handleSubmit} noValidate>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <TextField
                  autoComplete="given-name"
                  name="firstName"
                  required
                  fullWidth
                  id="firstName"
                  label="First Name"
                  autoFocus
                  onChange={handleChange}
                  value={formData.firstName}
                  error={!!errors.firstName}
                  helperText={errors.firstName}
                />
              </Grid>
              <Grid item xs={12} sm={6}>
                <TextField
                  required
                  fullWidth
                  id="lastName"
                  label="Last Name"
                  name="lastName"
                  autoComplete="family-name"
                  onChange={handleChange}
                  value={formData.lastName}
                  error={!!errors.lastName}
                  helperText={errors.lastName}
                />
              </Grid>
            </Grid>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
              onChange={handleChange}
              value={formData.email}
              error={!!errors.email}
              helperText={errors.email}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              onChange={handleChange}
              value={formData.password}
              error={!!errors.password}
              helperText={errors.password}
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="confirmPassword"
              label="Confirm Password"
              type="password"
              id="confirmPassword"
              autoComplete="current-password"
              onChange={handleChange}
              value={formData.confirmPassword}
              error={!!errors.confirmPassword}
              helperText={errors.confirmPassword}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 1 }}
              disabled={loading}
            >
              {loading ? <CircularProgress size={24} /> : "Sign Up"}
            </Button>
          </form>
        </Box>
        <Grid item xs={12} sm={6} style={{ marginLeft: "6%" }}>
          <Link to="/login">{"Have an account? Sign In"}</Link>
        </Grid>
      </Grid>
      <Snackbar open={showSuccessMessage} onClose={handleClose}>
        <MuiAlert onClose={handleClose} severity="success" variant="filled">
          Sign Up Successful...Redirecting to the Login Page
        </MuiAlert>
      </Snackbar>
    </Grid>
  );
}
