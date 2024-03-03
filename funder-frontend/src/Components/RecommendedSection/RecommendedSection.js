import React, { useState, useEffect } from "react";
import CardOverflow from "@mui/joy/CardOverflow";
import AspectRatio from "@mui/joy/AspectRatio";
import Card from "@mui/joy/Card";
import CardContent from "@mui/joy/CardContent";
import Chip from "@mui/joy/Chip";
import Typography from "@mui/joy/Typography";
import LinearProgress from "@mui/material/LinearProgress";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import Grid from "@mui/material/Grid";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import AttachMoney from "@mui/icons-material/AttachMoney";
import moment from "moment";
import "./RecommendedSection.css";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import config from "../../config";

export default function RecommendedSection({ tags }) {
  const navigate = useNavigate();
  const [recommendedProducts, setRecommendedProducts] = useState([]);
  const URL = config.URL_PATH;

  useEffect(() => {
    axios.get(`${URL}/products/all`).then((response) => {
      const sortedProducts = response.data.sort(
        (a, b) => b.raisedAmount - a.raisedAmount
      );
      const topProducts = sortedProducts.slice(0, 4);
      setRecommendedProducts(topProducts);
    });
  }, []);

  function calculatePercentage(raised, amount) {
    return (raised / amount) * 100;
  }

  function calculateDaysLeft(targetDate) {
    const targetDateTime = new Date(targetDate).getTime();
    const currentDate = new Date().getTime();
    const timeDifference = targetDateTime - currentDate;
    const daysLeft = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));
    return daysLeft;
  }

  return (
    <div className="recommended-section bg">
      <div className="head_text" id="head_txt">
        Recommended Section
      </div>

      <div className="adjust">
        <Grid container spacing={3}>
          {recommendedProducts
            .filter(
              (product) => calculateDaysLeft(product.dateOfRoundClosing) > 0
            )
            .map((product, index) => (
            <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                <Card
                  style={{
                    boxShadow: "lg",
                    marginBottom: 15,
                  }}
                  className="productCard"
                  onClick={()=>{
                     navigate("/productlist")
                  }}
                >
                  <CardOverflow>
                    <AspectRatio ratio="1.4">
                      <img
                        src={product.productImage}
                        alt={product.title}
                        className="productImage"
                      />
                    </AspectRatio>
                  </CardOverflow>
                  <CardContent>
                    <div>
                      <Typography variant="h3">
                        <span>
                          {product.category
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
                                      fontSize: "12px",
                                      marginRight:
                                        index < array.length - 1 ? "4px" : "0",
                                      marginBottom: "4px",
                                    }}
                                  >
                                    {category.title}
                                  </span>
                                )
                              )
                            : ""}
                        </span>
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
                      {moment(product.createdAt).format("DD, MMM YYYY")}
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
                            {calculateDaysLeft(product.dateOfRoundClosing)} days
                            left
                          </Chip>
                        </div>
                      }
                    ></Typography>
                  </CardContent>
                </Card>
              
            </Grid>
          ))}
        </Grid>
      </div>
      <div style={{ textAlign: "center", padding: "30px" }}>
        <Link to="/ProductList">
          <button className="see-more-button">See more</button>
        </Link>
      </div>
    </div>
  );
}
