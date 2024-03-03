import * as React from "react";
import axios from "axios";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import Typography from "@mui/material/Typography";
import logo from "../../Assets/funder-Logo.png";
import { Link } from "react-router-dom";
import pic from "../../Assets/reset-password.jpg";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";
import CircularProgress from "@mui/material/CircularProgress";
import config from "../../config";

export default function ForgotPassword() {
  const [formData, setFormData] = React.useState({
    email: "",
  });
  const URL = config.URL_PATH;
  const [errors, setErrors] = React.useState({});
  const [showSuccessMessage, setShowSuccessMessage] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

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

    if (!formData.email) {
      validationErrors.email = "Email is required";
    } else if (!/^\S+@\S+\.\S+$/.test(formData.email)) {
      validationErrors.email = "Invalid email address";
    }

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      try {
        setLoading(true);
        const apiUrl = `${URL}/user/send-forgot-password-email?userName=${formData.email}`;
        await axios.post(apiUrl);
        setShowSuccessMessage(true);
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
          backgroundImage: `url(${pic})`,
          backgroundRepeat: "no-repeat",
          backgroundSize: "cover",
          backgroundPosition: "center",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
        }}
      ></Grid>

      <Grid item xs={12} sm={8} md={5}>
        <Box
          sx={{
            my: 15,
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
            Forgot Password
          </Typography>
          <form onSubmit={handleSubmit} noValidate>
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

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
              disabled={loading}
            >
              {loading ? <CircularProgress size={24} /> : "Submit"}
            </Button>
          </form>
          <Grid
            item
            xs={2}
            sm={6}
            style={{ marginLeft: "3%", marginTop: "3%" }}
          >
            <Link to="/login">{"Remember Credentials? Sign In"}</Link>
          </Grid>
        </Box>
      </Grid>

      <Snackbar open={showSuccessMessage} onClose={handleClose}>
        <MuiAlert onClose={handleClose} severity="success" variant="filled">
          Please check your email to set the new password.
        </MuiAlert>
      </Snackbar>
    </Grid>
  );
}
