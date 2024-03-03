import React, { useState, useEffect } from "react";
import {
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Typography,
  Autocomplete,
} from "@mui/material";
import allCategories from "./categoriesConstant";
import moment from "moment/moment";
import axios from "axios";
import config from "../../../config";

const CustomImageInput = ({ currentImage, onImageChange }) => {
  const [image, setImage] = useState(currentImage);


  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
        onImageChange(file);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <div style={{ display: "flex", alignItems: "center", marginBottom: 16 }}>
      <input
        style={{ flex: 1 }}
        type="file"
        accept="image/*"
        onChange={handleImageChange}
      />
      <div>
        {image && (
          <img
            src={image}
            alt="Product"
            style={{ marginLeft: 8, maxHeight: 100, flex: 1 }}
          />
        )}
      </div>
    </div>
  );
};

const AddFounderForm = ({ open, onClose, onSave, initialFormData }) => {
  const userid = localStorage.getItem("id");
  const [imageFile, setImageFile] = useState(null);

  const [formData, setFormData] = useState({
    title: "",
    description: "",
    dateOfRoundClosing: "",
    fundingAmount: "",
    percentage: "",
    productImage: null,
    category: [],
    user: { id: userid },
  });

  useEffect(() => {
    if (initialFormData) {
      const formattedDate = moment(initialFormData.dateOfRoundClosing)
        .utc()
        .format("YYYY-MM-DD");
      setFormData({ ...initialFormData, dateOfRoundClosing: formattedDate });
      setImageFile(initialFormData.productImage);
    } else {
      resetForm();
      setImageFile(null);
    }
  }, [initialFormData]);

  useEffect(() => {
    // Reset the form when it is opened for a new product
    if (open && !initialFormData) {
      resetForm();
    }
  }, [open, initialFormData]);

  const resetForm = () => {
    setFormData({
      title: "",
      description: "",
      dateOfRoundClosing: "",
      fundingAmount: "",
      percentage: "",
      productImage: null,
      category: [],
      user: { id: userid },
    });
  };

  const handleInputChange = (e) => {
    const { name, value, type, files } = e.target;

    if (type === "file") {
      const inputValue = files.length > 0 ? files[0] : null;
      setFormData({ ...formData, [name]: inputValue });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleImageChange = (file) => {
    setImageFile(file);
  };

  const handleCategoriesChange = (event, newValue) => {
    setFormData({ ...formData, category: newValue });
  };

  const handleSave = async () => {
    const accessToken = localStorage.getItem("token");
    const URL = config.URL_PATH;
    const apiUrl = initialFormData
      ? `${URL}/products/update/${initialFormData.id}`
      : `${URL}/products/add`;

    try {
      const formDataApi = new FormData();
      const payload = { ...formData };
      payload.category = JSON.stringify(payload.category);
      delete payload.productImage;

      !initialFormData
        ? formDataApi.append("product", JSON.stringify(payload))
        : formDataApi.append("updatedProduct", JSON.stringify(payload));
      !initialFormData && formDataApi.append("image", imageFile);

      const response = initialFormData
        ? await axios.put(apiUrl, formDataApi, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${accessToken}`,
            },
          })
        : await axios.post(apiUrl, formDataApi, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${accessToken}`,
            },
          });

      onSave(response.data);
      setImageFile(null);

      onClose();
    } catch (error) {
      console.error("Error adding/updating founder:", error);
    }
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>
        <Typography variant="h5" align="center">
          {initialFormData ? "Edit Details" : "Add New Product"}
        </Typography>
      </DialogTitle>
      <DialogContent>
        <TextField
          name="title"
          label="Title"
          fullWidth
          value={formData.title}
          onChange={handleInputChange}
          sx={{ mb: 2, mt: 2 }}
        />
        <TextField
          name="description"
          label="Description"
          fullWidth
          value={formData.description}
          onChange={handleInputChange}
          sx={{ mb: 2, mt: 2 }}
        />
        <Autocomplete
          multiple
          id="category"
          options={allCategories}
          getOptionLabel={(option) => option.title}
          value={formData.category}
          onChange={handleCategoriesChange}
          renderInput={(params) => (
            <TextField
              {...params}
              name="category"
              label="Categories"
              fullWidth
              sx={{ mb: 2 }}
            />
          )}
        />
        <TextField
          name="dateOfRoundClosing"
          label="Date of Round Closing"
          type="date"
          fullWidth
          value={formData.dateOfRoundClosing}
          onChange={handleInputChange}
          InputLabelProps={{
            shrink: true,
          }}
          placeholder="Select a date"
          sx={{ mb: 2 }}
          inputProps={{
            min: moment().format("YYYY-MM-DD"),
          }}
        />

        <TextField
          name="fundingAmount"
          label="Total Investment Seeking"
          fullWidth
          value={formData.fundingAmount}
          onChange={handleInputChange}
          sx={{ mb: 2 }}
        />

        <TextField
          name="percentage"
          label="Percentage Dilution"
          fullWidth
          value={formData.percentage}
          onChange={handleInputChange}
          sx={{ mb: 2 }}
        />

        {initialFormData ? (
          <img
            src={formData.productImage}
            alt="Product"
            style={{ marginLeft: 8, maxHeight: 100 }}
          />
        ) : (
          <CustomImageInput
            currentImage={imageFile}
            onImageChange={handleImageChange}
          />
        )}

        {/* <Typography
            variant="body1"
            color="textSecondary"
            sx={{ ml: 2 }}
          >
            Upload image for your product
          </Typography> */}
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose}>Close</Button>
        <Button onClick={handleSave}>Save</Button>
      </DialogActions>
    </Dialog>
  );
};

export default AddFounderForm;
