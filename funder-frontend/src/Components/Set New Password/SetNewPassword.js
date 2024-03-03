import React, { useState } from "react";
import axios from "axios";
import "./SetNewPassword.css";
import logo from "../../Assets/funder-Logo.png";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import setpassword from "../../Assets/set_new_password.jpg";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";
import CircularProgress from "@mui/material/CircularProgress";
import Navbar from "../Common/Navbar";
import config from "../../config";

export default function SetNewPassword() {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const token = searchParams.get("token");
  const URL = config.URL_PATH;
  const [formData, setFormData] = useState({
    newpassword: "",
    confirmnewpassword: "",
  });

  const [errors, setErrors] = React.useState({});
  const [showSuccessMessage, setShowSuccessMessage] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);
  const navigate = useNavigate();

  React.useEffect(() => {
    checkIfLoggedIn();
  }, []);

  function checkIfLoggedIn() {
    const storedFirstName = localStorage.getItem("firstName");
    if (storedFirstName !== null) {
      setIsLoggedIn(true);
    }
  }

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
    if (!formData.newpassword) {
      validationErrors.newpassword = "Password is required";
    } else if (
      !/^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/.test(
        formData.newpassword
      )
    ) {
      validationErrors.newpassword =
        "Password must contain at least 1 capital letter, 1 numerical digit, and 1 special character (!@#$%^&*)";
    }
    if (formData.newpassword !== formData.confirmnewpassword) {
      validationErrors.confirmnewpassword = "Passwords do not match";
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      try {
        setLoading(true);
        const apiUrl = `${URL}/user/reset-password`;
        const requestBody = {
          token,
          newPassword: formData.newpassword,
        };

        const response = await axios.put(apiUrl, requestBody);
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
    <>
      {false
        ? [
            <div>
              <Navbar />
              <div>
                <text> Unauthorized </text>
              </div>
            </div>,
          ]
        : [
            <div className="setNewPassword">
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
                  <img
                    src={setpassword}
                    alt="Set New Password"
                    className="picreset"
                  />
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
                      <img src={logo} alt="Logo" className="logo" />
                    </div>
                    <Typography
                      component="h1"
                      variant="h5"
                      mt={2}
                      mb={2}
                      style={{ fontStyle: "italic" }}
                    >
                      Set Password
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
                        name="newpassword"
                        label="New Password"
                        type="password"
                        id="newpassword"
                        onChange={handleChange}
                        value={formData.newpassword}
                        error={!!errors.newpassword}
                        helperText={errors.newpassword}
                      />
                      <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="confirmnewpassword"
                        label="Confirm New Password"
                        type="password"
                        id="confirmnewpassword"
                        onChange={handleChange}
                        value={formData.confirmnewpassword}
                        error={!!errors.confirmnewpassword}
                        helperText={errors.confirmnewpassword}
                      />
                      <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        disabled={loading} // Disable the button when loading
                      >
                        {loading ? <CircularProgress size={24} /> : "Submit"}
                      </Button>

                      <Grid container></Grid>
                    </Box>
                  </Box>
                </Grid>
                <Snackbar open={showSuccessMessage} onClose={handleClose}>
                  <MuiAlert
                    onClose={handleClose}
                    severity="success"
                    variant="filled"
                  >
                    Password Reset Successful...Redirecting to the login page
                  </MuiAlert>
                </Snackbar>
              </Grid>
            </div>,
          ]}
    </>
  );
}
