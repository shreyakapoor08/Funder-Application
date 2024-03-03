import React, { useState, useEffect } from "react";
import CardOverflow from "@mui/joy/CardOverflow";
import AspectRatio from "@mui/joy/AspectRatio";
import Card from "@mui/joy/Card";
import CardContent from "@mui/joy/CardContent";
import Chip from "@mui/joy/Chip";
import Typography from "@mui/joy/Typography";
import LinearProgress from "@mui/material/LinearProgress";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import "./ProductList.css";
import Grid from "@mui/material/Grid";
import InputBase from "@mui/material/InputBase";
import SearchIcon from "@mui/icons-material/Search";
import Navbar from "../Common/Navbar";
import ToggleButton from "@mui/material/ToggleButton";
import { useNavigate } from "react-router-dom";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import AttachMoney from "@mui/icons-material/AttachMoney";
import moment from "moment";
import axios from "axios";
import config from '../../config';

export default function ProductList() {
  const navigate = useNavigate();
  const [productList, setProductList] = useState([]);
  const [selectedOptions, setSelectedOptions] = useState(["All"]);
  const [searchText, setSearchText] = useState("");
  const URL = config.URL_PATH;

  function handleButtonClick(option) {
    if (selectedOptions.length === 0 && selectedOptions.length === 1) {
      setSelectedOptions(["All"]);
    } else {
      if (option === "All") {
        setSelectedOptions(["All"]);
      } else if (selectedOptions.includes(option)) {
        setSelectedOptions(
          selectedOptions.filter((selected) => selected !== option)
        );
      } else {
        if (selectedOptions.length === 1 && selectedOptions[0] === "All") {
          setSelectedOptions([option]);
        } else {
          setSelectedOptions([...selectedOptions, option]);
        }
      }
    }
    checkIfEmpty();
  }

  function checkIfEmpty() {
    if (selectedOptions.length === 0) {
      setSelectedOptions(["All"]);
    }
  }

  function handleSearchInputChange(event) {
    setSearchText(event.target.value);
  }

  function handleCardClick(pId, uId) {
    navigate("/productDetails", { state: { id: pId, userId: uId } });
  }

  function calculatePercentage(raised, amount) {
    return (raised / amount) * 100;
  }

  const handleGetProduct = async (url) => {
    try {
      const response = await axios.get(url);
      setProductList(response.data);
    } catch (error) {
      console.error("API Error:", error);
    }
  };

  useEffect(() => {
    handleGetProduct(`${URL}/products/all`);
  }, []);

  function calculateDaysLeft(targetDate) {
    const targetDateTime = new Date(targetDate).getTime();
    const currentDate = new Date().getTime();
    const timeDifference = targetDateTime - currentDate;
    const daysLeft = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));
    return daysLeft;
  }

  const options = [
    "All",
    "Education",
    "Human Rights",
    "Audio",
    "Transportation",
    "Camera Gear",
    "Music",
    "Writing & Publishing",
    "Film",
  ];

  const filteredList = productList.filter((product) => {
    const isSelectedCategory =
      selectedOptions.includes("All") ||
      selectedOptions.some((option) => product.category.includes(option));

    return (
      isSelectedCategory &&
      (searchText === "" ||
        product.title.toLowerCase().includes(searchText.toLowerCase()))
    );
  });

  return (
    <>
      <div className="container" style={{ overflow: "hidden" }}>
        <Navbar />
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <div className="filterOption">
            {options.map((option) => (
              <ToggleButton
                key={option}
                value={option}
                selected={selectedOptions.includes(option)}
                onClick={() => handleButtonClick(option)}
                style={{
                  borderRadius: "30px",
                  backgroundColor: selectedOptions.includes(option)
                    ? "#1976D2"
                    : "#F2F6FB",
                  color: selectedOptions.includes(option) ? "white" : "black",
                  border: `1px solid ${
                    selectedOptions.includes(option) ? "white" : "white"
                  }`,
                  padding: "7px",
                  margin: "0px 3px",
                  minWidth: "100px",
                  textAlign: "center",
                }}
              >
                {option}
              </ToggleButton>
            ))}
          </div>
          <div className="searchOption">
            <InputBase
              placeholder="Search..."
              startAdornment={<SearchIcon />}
              style={{
                backgroundColor: "#F2F6FB",
                borderRadius: "30px",
                padding: "5px 15px",
              }}
              value={searchText}
              onChange={handleSearchInputChange}
            />
          </div>
        </div>

        <Grid container>
          {filteredList
            .filter(
              (product) => calculateDaysLeft(product.dateOfRoundClosing) > 0
            )
            .map((product, index) => {
              // if (!product.active) {
              //   return null;
              // }
              return (
                <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                  <Card
                    style={{
                      marginRight: 10,
                      marginLeft: 10,
                      boxShadow: "lg",
                      marginBottom: 15,
                      maxHeight: "100%",
                      overflow: "hidden",
                    }}
                    onClick={() => handleCardClick(product.id, product.user.id)}
                    className="productCard"
                  >
                    <CardOverflow>
                      <AspectRatio ratio="1.4">
                        <img
                          src={product.productImage}
                          alt={product.title}
                          classname="productImage"
                        />
                      </AspectRatio>
                    </CardOverflow>
                    <CardContent>
                      <div>
                        <Typography variant="h3">
                          <div>
                            {product && product.category
                              ? JSON.parse(product.category).map(
                                  (category, index, array) => (
                                    <span
                                      key={index}
                                      style={{
                                        background: "#5E17EB",
                                        display: "inline-block",
                                        padding: "2px 6px",
                                        borderRadius: "4px",
                                        color: "white",
                                        fontSize: "16px",
                                        marginRight:
                                          index < array.length - 1
                                            ? "4px"
                                            : "0",
                                        marginBottom: "4px",
                                      }}
                                    >
                                      {category.title}
                                    </span>
                                  )
                                )
                              : ""}
                          </div>
                        </Typography>
                      </div>
                      <Typography
                        variant="body2"
                        color="textSecondary"
                        style={{
                          fontWeight: "bold",
                          fontSize: "18px",
                          color: "black",
                          marginTop: "8px",
                        }}
                      >
                        {product.title}
                      </Typography>
                      <Typography
                        variant="body2"
                        color="textSecondary"
                        style={{
                          display: "flex",
                          alignItems: "center",
                          marginTop: "8px",
                        }}
                      >
                        <CalendarMonthIcon
                          sx={{ color: "black", marginRight: 2 }}
                        />
                        {moment(product.dateOfRoundClosing).format(
                          "DD, MMM YYYY"
                        )}
                      </Typography>
                      <Typography
                        variant="body2"
                        color="textSecondary"
                        style={{
                          display: "flex",
                          alignItems: "center",
                          marginTop: "3px",
                          marginBottom: "4px",
                        }}
                      >
                        <AttachMoney sx={{ color: "black", marginRight: 2 }} />
                        {product.raisedAmount} / {product.fundingAmount}
                      </Typography>
                      <LinearProgress
                        variant="determinate"
                        value={calculatePercentage(
                          product.raisedAmount,
                          product.fundingAmount
                        )}
                        style={{ height: "6px", borderRadius: "3px" }}
                      />
                      <Typography
                        level="title-lg"
                        sx={{ mt: 1, fontWeight: "xl" }}
                        endDecorator={
                          <div
                            style={{
                              display: "flex",
                              alignItems: "center",
                              marginRight: "8px",
                            }}
                          >
                            <AccessTimeIcon sx={{ marginRight: "4px" }} />
                            <Chip
                              component="span"
                              size="sm"
                              variant="soft"
                              color="success"
                            >
                              {calculateDaysLeft(product.dateOfRoundClosing)}{" "}
                              days left
                            </Chip>
                          </div>
                        }
                      ></Typography>
                    </CardContent>
                  </Card>
                </Grid>
              );
            })}
        </Grid>
      </div>
    </>
  );
}
