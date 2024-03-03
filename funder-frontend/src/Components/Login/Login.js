import React, { useState } from "react";
import axios from "axios";
import "./Login.css";
import logo from "../../Assets/funder-Logo.png";
import LogIN from "../../Assets/LogIN.png";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import { Link, useNavigate } from "react-router-dom";
import CircularProgress from "@mui/material/CircularProgress";
import MuiAlert from "@mui/material/Alert";
import Snackbar from "@mui/material/Snackbar";
import config from "../../config";

function Login() {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const URL = config.URL_PATH;
  const [loading, setLoading] = React.useState(false);
  const navigate = useNavigate();
  const [showSuccessMessage, setShowSuccessMessage] = React.useState(false);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setShowSuccessMessage(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setLoading(true);
      const response = await axios.post(
        `${URL}/user/login`,
        {
          userName: formData.email,
          password: formData.password,
        }
      );

      setShowSuccessMessage(true);
      localStorage.setItem("id", response.data.id);
      localStorage.setItem("userName", response.data.userName);
      localStorage.setItem("firstName", response.data.firstName);
      localStorage.setItem("token", response.data.token);

      setTimeout(() => {
        navigate("/");
      }, 2500);
    } catch (error) {
      console.error("API Error:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  return (
    <div className="loginBody">
      <Grid container component="main" sx={{ height: "100vh" }}>
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            backgroundSize: "cover",
            backgroundPosition: "center",
            backgroundColor: "#ffffff",
          }}
        >
          <img src={LogIN} alt="Logo" className="picLogin" />
        </Grid>
        <Grid
          item
          xs={12}
          sm={8}
          md={5}
          sx={{
            backgroundSize: "cover",
            backgroundPosition: "center",
            backgroundColor: "#ffffff",
          }}
        >
          <Box
            sx={{
              my: 8,
              mx: 4,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              alignSelf: "center",
              marginTop: "26%",
              backgroundColor: "#ffffff",
            }}
          >
            <div className="logoDiv">
              <img src={logo} alt="Logo" class="login_logo" />
            </div>
            <Typography
              component="h1"
              variant="h5"
              mt={2}
              mb={2}
              style={{ fontStyle: "italic" }}
            >
              Log In
            </Typography>
            <Box
              component="form"
              onSubmit={handleSubmit}
              noValidate
              sx={{ mt: 1 }}
            >
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                name="email"
                label="Email Address"
                autoComplete="email"
                autoFocus
                value={formData.email}
                onChange={handleInputChange}
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                autoComplete="current-password"
                value={formData.password}
                onChange={handleInputChange}
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 1, mb: 2 }}
                disabled={loading} // Disable the button when loading
              >
                {loading ? <CircularProgress size={24} /> : "Login"}
              </Button>
              <Grid container>
                <Grid item xs={12} sm={6}>
                  <Link to="/send-forgot-password-email">Forgot password?</Link>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <Link to="/signup">{"Don't have an account? Sign Up"}</Link>
                </Grid>
              </Grid>
              <Snackbar open={showSuccessMessage} onClose={handleClose}>
                <MuiAlert
                  onClose={handleClose}
                  severity="success"
                  variant="filled"
                >
                  Login Successful...Redirecting to the Home Page
                </MuiAlert>
              </Snackbar>
            </Box>
          </Box>
        </Grid>
      </Grid>
    </div>
  );
}

export default Login;
