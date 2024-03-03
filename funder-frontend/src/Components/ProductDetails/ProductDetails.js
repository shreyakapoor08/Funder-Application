import React, { useEffect, useState } from "react";
import {
  Box,
  Card,
  CardContent,
  CardMedia,
  LinearProgress,
  Typography,
  Button,
  Grid,
  Tabs,
  Tab,
  TextField,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import Navbar from "../Common/Navbar";
import "./ProductDetails.css";
import logo from "../../Assets/funder-Logo.png";
import axios from "axios";
import { useLocation } from "react-router-dom";
import config from "../../config";

export const ProductDetails = () => {
  const URL = config.URL_PATH
  const state = useLocation();
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState(0);
  const [investmentAmount, setInvestmentAmount] = useState(0);
  const [paymentStatus, setPaymentStatus] = useState(false);
  const [productId, setProductId] = useState();
  const [title, setTitle] = useState();
  const [amount, setAmount] = useState();
  const [percentage, setPercentage] = useState();
  const [raisedamount, setRaisedAmount] = useState();
  const [image, setImage] = useState();
  const [daysLeft, setDaysLeft] = useState();
  const [firstName, setFirstName] = useState();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [newCommentText, setNewCommentText] = useState();
  const [commentsData, setCommentsData] = useState();
  const [comLength, setComLength] = useState();
  const [userId, setUserId] = useState();
  const [description, setDescription] = useState();
  const [paymentSuccessModalOpen, setPaymentSuccessModalOpen] = useState(false);

  const handleGetProduct = async (url) => {
    try {
      const response = await axios.get(url);
      setTitle(response.data.title);
      setAmount(response.data.fundingAmount);
      setRaisedAmount(response.data.raisedAmount);
      setImage(response.data.productImage);
      setPercentage(response.data.percentage);
      setDescription(response.data.description);
      calculateDaysLeft(response.data.dateOfRoundClosing);
    } catch (error) {
      console.error("API Error:", error);
    }
  };

  useEffect(() => {
    loadRazorpayScript();
    const pid = state;
    setProductId(pid.state.id);
    setUserId(pid.state.userId);
    getFirstNameFromLocalStorage();
    handleGetProduct(`${URL}/products/${pid.state.id}`);
    axios
      .get(
        `${URL}/comments/getCommentsByProduct/${pid.state.id}`
      )
      .then((response) => {
        setCommentsData(response.data);
        setComLength(response.data.length);
      });
  }, []);

  const getFirstNameFromLocalStorage = () => {
    const storedFirstName = localStorage.getItem("firstName");
    if (storedFirstName != null) {
      setFirstName(storedFirstName);
      setIsLoggedIn(true);
    }
  };

  const handleTabChange = (event, newValue) => {
    setSelectedTab(newValue);
  };

  const handleAmountChange = (event) => {
    setInvestmentAmount(event.target.value);
    checkValue();
  };

  const isInvestButtonDisabled = () => {
    if(!isLoggedIn){
      return true
    }
    return !investmentAmount || investmentAmount <= 0 || checkValue();
  };

  const checkValue = () => {
    const maxInvestmentAmount = amount - raisedamount;

    if (investmentAmount > maxInvestmentAmount) {
      return true;
    }

    return false;
  };

  const loadRazorpayScript = async () => {
    const script = document.createElement("script");
    script.src = "https://checkout.razorpay.com/v1/checkout.js";
    script.async = true;
    document.body.appendChild(script);
  };

  const Comment = ({ comment, onReply, level = 0 }) => {
    const [replyText, setReplyText] = useState("");

    const handleReply = () => {
      if (replyText.trim() !== "") {
        onReply(comment.comId, replyText);
        setReplyText("");
      }
    };

    return (
      <div style={{ marginLeft: `${level * 20}px`, marginBottom: "10px" }}>
        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: "10px",
            margin: "10px 10px",
          }}
        >
          <img
            src={comment.avatarUrl}
            alt="Avatar"
            style={{ borderRadius: "50%", width: "30px", height: "30px" }}
          />
          <strong>{comment.fullName}</strong>: {comment.text}
        </div>
        {level < 1 && (
          <div style={{ marginLeft: "45px", marginBottom: "20px" }}>
            <input
              type="text"
              value={replyText}
              onChange={(e) => setReplyText(e.target.value)}
              placeholder="Reply..."
              style={{
                border: "none",
                borderBottom: "1px solid black",
                flex: "1",
                fontSize: "14px",
                marginLeft: "6px",
                marginRight: "10px",
                outline: "none",
                marginBottom: "5px",
              }}
              disabled={!isLoggedIn}
            />
            <button
              onClick={handleReply}
              style={{
                fontSize: "13px",
                borderRadius: "10px",
                color: "#1976D2",
                padding: "5px 10px",
                cursor: "pointer",
                border: "2px solid #1976D2",
              }}
              disabled={!isLoggedIn}
            >
              Reply
            </button>
          </div>
        )}
        {comment.replies && comment.replies.length > 0 && (
          <div style={{ marginLeft: "20px" }}>
            {comment.replies.map((reply) => (
              <Comment
                key={reply.comId}
                comment={reply}
                onReply={onReply}
                level={level + 1}
              />
            ))}
          </div>
        )}
      </div>
    );
  };

  const handleAddComment = async () => {
    if (newCommentText.trim() !== "") {
      const commentId = Date.now() % 1000;
      const newCommentjson = {
        userId: userId,
        comId: commentId,
        fullName: firstName,
        text: newCommentText,
        avatarUrl: `https://ui-avatars.com/api/name=${firstName}&background=random`,
        replies: [],
        productId: productId,
      };
      setNewCommentText("");
      let updatedComments = commentsData || [];
      updatedComments.push(newCommentjson);
      setCommentsData(updatedComments);
      setComLength(updatedComments.length);
      const newCommentjsonString = JSON.stringify(newCommentjson);
      await axios
        .post(
          `${URL}/comments/saveComment/`,
          newCommentjsonString,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {
          // Handle response if needed
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    }
  };

  const CommentSection = ({ comments }) => {
    const [newCommentText, setNewCommentText] = useState("");

    const handleAddComment = async () => {
      if (newCommentText.trim() !== "") {
        const commentId = Date.now() % 1000;
        const newCommentjson = {
          userId: userId,
          comId: commentId,
          fullName: firstName,
          text: newCommentText,
          avatarUrl: `https://ui-avatars.com/api/name=${firstName}&background=random`,
          replies: [],
          productId: productId,
        };
        setNewCommentText("");
        let updatedComments = commentsData || [];
        updatedComments.push(newCommentjson);
        setCommentsData(updatedComments);
        setComLength(updatedComments.length);
        const newCommentjsonString = JSON.stringify(newCommentjson);
        await axios
          .post(
            `${URL}/comments/saveComment/`,
            newCommentjsonString,
            {
              headers: {
                "Content-Type": "application/json",
              },
            }
          )
          .then((response) => {})
          .catch((error) => {
            console.error("Error:", error);
          });
      }
    };

    const handleReply = (parentId, replyText) => {
      const { updatedComments, updatedObject } = updateNestedReplies(
        commentsData,
        parentId,
        replyText
      );
      updatedObject.productId = productId;
      const newReplyjsonString = JSON.stringify(updatedObject);
      axios
        .post(
          `${URL}/comments/saveComment/`,
          newReplyjsonString,
          {
            headers: {
              "Content-Type": "application/json",
            },
          }
        )
        .then((response) => {})
        .catch((error) => {
          console.error("Error:", error);
        });

      setCommentsData(updatedComments);
    };

    const updateNestedReplies = (comments, parentId, replyText) => {
      let updatedObject = null;
      const commentId = Date.now() % 1000;
      const updatedComments = comments.map((comment) => {
        if (comment.comId === parentId) {
          updatedObject = {
            ...comment,
            replies: [
              ...(comment.replies || []),
              {
                userId: userId,
                comId: commentId,
                fullName: firstName,
                text: replyText,
                avatarUrl: `https://ui-avatars.com/api/name=${firstName}&background=random`,
              },
            ],
          };
          return updatedObject;
        } else if (comment.replies && Array.isArray(comment.replies)) {
          return {
            ...comment,
            replies: updateNestedReplies(comment.replies, parentId, replyText),
          };
        }
        return comment;
      });

      return { updatedComments, updatedObject };
    };

    return (
      <div>
        {!isLoggedIn ? (
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              border: "2px solid #B6B6B6",
              padding: "2%",
              color: "#B6B6B6",
              borderRadius: "7px",
              marginBottom: "2%",
            }}
          >
            <span
              style={{
                fontWeight: "bold",
                fontSize: "22px",
                marginTop: "0.6%",
              }}
            >
              Login or Signup to add a comment
            </span>
            <div>
              <button
                style={{
                  fontSize: "16px",
                  borderRadius: "22px",
                  backgroundColor: "#1976D2",
                  color: "white",
                  padding: "14px 24px",
                  cursor: "pointer",
                  border: "none",
                  outline: "none",
                  marginRight: "5px"
                }}
                onClick={() => {
                  navigate('/login');
                }}
              >
                Login
              </button>
              <button
                style={{
                  fontSize: "16px",
                  borderRadius: "22px",
                  color: "#1976D2",
                  padding: "12px 22px",
                  cursor: "pointer",
                  border: "2px solid #1976D2",
                }}
                onClick={() => {
                  navigate('/signup');
                }}
              >
                Signup
              </button>
            </div>
          </div>
        ) : (
          <div
            style={{
              marginBottom: "20px",
              display: "flex",
              alignItems: "center",
              background: "#F3F3F3",
              borderRadius: "8px",
              padding: "24px ",
            }}
          >
            <img
              src={`https://ui-avatars.com/api/name=${firstName}&background=random`}
              alt="Avatar"
              style={{
                borderRadius: "50%",
                width: "30px",
                height: "30px",
                marginRight: "10px",
              }}
            />
            <input
              type="text"
              value={newCommentText}
              onChange={(e) => setNewCommentText(e.target.value)}
              placeholder="Add a new comment..."
              style={{
                border: "none",
                borderBottom: "1px solid black",
                flex: "1",
                fontSize: "14px",
                backgroundColor: "#F3F3F3",
                marginLeft: "6px",
                marginRight: "10px",
                outline: "none",
              }}
            />
            <button
              onClick={handleAddComment}
              style={{
                fontSize: "16px",
                borderRadius: "20px",
                backgroundColor: "#1976D2",
                color: "white",
                padding: "10px 20px",
                cursor: "pointer",
                border: "none",
                outline: "none",
              }}
            >
              Add Comment
            </button>
          </div>
        )}

        {commentsData && commentsData.length > 0 ? (
          commentsData.map((comment) => (
            <Comment
              key={comment.comId}
              comment={comment}
              onReply={handleReply}
            />
          ))
        ) : (
          <p>No comments available.</p>
        )}
      </div>
    );
  };

  const calculatePercentage = (raised, amount) => {
    return Math.round((amount / raised) * 100);
  };

  const calculateDaysLeft = (targetDate) => {
    const targetDateTime = new Date(targetDate).getTime();
    const currentDate = new Date().getTime();
    const timeDifference = targetDateTime - currentDate;
    const daysLeft = Math.ceil(timeDifference / (1000 * 60 * 60 * 24));
    setDaysLeft(daysLeft);
  };

  function calculateEquityPercentage(
    totalEquityDilution,
    totalInvestment,
    specificInvestment
  ) {
    const equityPercentage = (
      (specificInvestment / totalInvestment) *
      totalEquityDilution
    ).toFixed(2);
    return equityPercentage;
  }

  const calculateInvestedPercentage = (raisedamt, amt) => {
    let percentage = Math.round((raisedamt / amt) * 100);
    return percentage;
  };
  const handlePaymentSuccessModalClose = () => {
    setPaymentSuccessModalOpen(false);

    setTimeout(() => {
      window.location.href = "/dashboard/investment";
    }, 1500);
  };
  const openRazorpayCheckout = async () => {
    try {
      const token = localStorage.getItem("token");
      const name = localStorage.getItem("firstName");
      const userName = localStorage.getItem("userName");
      const userId = localStorage.getItem("id");
      const amount = investmentAmount;

      const requestBody = {
        amount: amount,
        currency: "CAD",
      };

      const { data } = await axios({
        method: "POST",
        url: `${URL}/payment/razorpay`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
        data: requestBody,
      });

      const options = {
        key: "rzp_test_YVJU3eyNoJTnWC",
        amount: amount * 100,
        currency: "CAD",
        name: "Funder",
        order_id: data.id,
        description: "Test Transaction",
        image: logo,
        handler: async function (response) {
          if (response.razorpay_payment_id) {
            setPaymentStatus(true);
            const requestBodyInvestment = {
              investorId: userId,
              amount: Number(amount),
              productId: productId,
              razorPayPaymentId: response.razorpay_payment_id,
              paymentStatus: true,
            };
            const { investment } = await axios({
              method: "POST",
              url: `${URL}/investedProduct/saveInvestment`,
              headers: {
                Authorization: `Bearer ${token}`,
              },
              data: requestBodyInvestment,
            });

            setPaymentSuccessModalOpen(true);
          }
        },
        prefill: {
          name: name,
          email: userName,
        },
        notes: {
          address: "Razorpay Corporate Office",
        },
        theme: {
          color: "#3399cc",
        },
      };
      if (window.Razorpay) {
        const rzp = new window.Razorpay(options);
        rzp.open();
      } else {
        alert(
          "Razorpay SDK failed to load. Please check your internet connection."
        );
      }
    } catch (error) {
      console.error("Error processing payment:", error);
    }
  };

  return (
    <>
      <Navbar />
      <Card className="cardStyle" elevation={0} square={true}>
        <Grid container>
          <Grid item xs={12} sm={6}>
            <CardMedia
              component="img"
              height="max"
              image={image}
              alt="Product Image"
            />
          </Grid>
          <Grid item xs={12} sm={6}>
            <CardContent
              style={{
                marginLeft: "20px",
                marginRight: "15px",
                paddingTop: "0px",
              }}
            >
              <Box>
                <Typography
                  variant="h6"
                  gutterBottom
                  className="detailTitle"
                  style={{ color: "#088366" }}
                >
                  Funding
                </Typography>
                <Typography
                  variant="h5"
                  gutterBottom
                  sx={{ fontWeight: "bold", fontSize: "24px" }}
                >
                  {title}
                </Typography>
              </Box>
              <Box
                display="flex"
                alignItems="center"
                mt={3.5}
                mb={3.5}
                ml={1.5}
              >
                <img
                  src={image}
                  alt="Profile"
                  style={{
                    borderRadius: "50%",
                    width: "40px",
                    height: "40px",
                  }}
                />
                <Grid container direction="column">
                  <Typography
                    variant="body1"
                    ml={2}
                    sx={{ fontWeight: "bold" }}
                  >
                    Info Giants
                  </Typography>
                </Grid>
              </Box>
              <Box mb={1.3}>
                <Grid
                  container
                  alignItems="center"
                  direction="row"
                  justifyContent="space-between"
                >
                  <div style={{ display: "flex" }}>
                    <Typography variant="h5" sx={{ fontWeight: "bold" }}>
                      $ {raisedamount} / {amount}{" "}
                    </Typography>
                    <Typography variant="h6" style={{ marginLeft: "4px" }}>
                      {" "}
                      CAD{" "}
                    </Typography>
                  </div>
                  <Box
                    ml={2}
                    display="flex"
                    border="2px solid #1976D2"
                    borderRadius="8px"
                  >
                    <Box
                      bgcolor="#1976D2"
                      paddingTop={0.5}
                      paddingBottom={0.5}
                      paddingLeft={2.5}
                      paddingRight={2.5}
                    >
                      <Typography
                        variant="body2"
                        color="white"
                        style={{ fontSize: "16px" }}
                      >
                        Equity Dilution
                      </Typography>
                    </Box>
                    <Box paddingTop={0.5} paddingBottom={0.5}>
                      <Typography
                        variant="body2"
                        color="black"
                        style={{ fontWeight: "bold", fontSize: "16px" }}
                        ml={2}
                        mr={2}
                      >
                        {percentage}%
                      </Typography>
                    </Box>
                  </Box>
                </Grid>
              </Box>
              <LinearProgress
                variant="determinate"
                value={calculateInvestedPercentage(raisedamount, amount)}
                style={{ height: "8px", borderRadius: "4px" }}
              />
              <Box
                display="flex"
                justifyContent="space-between"
                mt={1.5}
                mb={4}
              >
                <Typography variant="body2" style={{ fontSize: "16px" }}>
                  {calculatePercentage(amount, raisedamount)}% of ${amount}
                </Typography>
                <Typography variant="body2" style={{ fontSize: "16px" }}>
                  {daysLeft} days left
                </Typography>
              </Box>
              <div style={{ display: "flex", alignItems: "center" }}>
                <TextField
                  label="Enter amount"
                  placeholder="CAD"
                  variant="outlined"
                  size="medium"
                  value={investmentAmount}
                  onChange={handleAmountChange}
                  style={{ marginRight: "10px" }}
                />
                <Button
                  onClick={openRazorpayCheckout}
                  variant="contained"
                  className="investBtn"
                  style={{
                    width: "110px",
                    backgroundColor:  isInvestButtonDisabled()
                      ? "#64B5F6"
                      : "#1976D2",
                    color: "white",
                    fontWeight: 400,
                    marginLeft: "15px",
                    height: "54px",
                  }}
                  disabled={isInvestButtonDisabled()}
                >
                  Invest
                </Button>
              </div>
              <div className="equityBox">
                {checkValue()
                  ? [
                      <text className="ErrorequityText">
                        The amount entered is greater than accepted amount!
                      </text>,
                    ]
                  : [
                      <text className="equityText">
                        You will get{" "}
                        {calculateEquityPercentage(
                          percentage,
                          amount,
                          investmentAmount
                        )}
                        % Equity on the invested amount.
                      </text>,
                    ]}
              </div>
            </CardContent>
          </Grid>
        </Grid>
      </Card>
      <div>
        <Box ml={13.3}>
          <Tabs
            value={selectedTab}
            onChange={handleTabChange}
            indicatorColor="primary"
            textColor="primary"
          >
            <Tab label="Discussion" />
            <Tab label="Information" />
          </Tabs>
          {selectedTab === 0 && (
            <div className="tabsDiv">
              {comLength === 0 ? (
                <>
                  {isLoggedIn
                    ? [
                        <div
                          style={{
                            marginBottom: "20px",
                            display: "flex",
                            alignItems: "center",
                            background: "#F3F3F3",
                            borderRadius: "8px",
                            padding: "24px ",
                          }}
                        >
                          <img
                            src={`https://ui-avatars.com/api/name=${firstName}&background=random`}
                            alt="Avatar"
                            style={{
                              borderRadius: "50%",
                              width: "30px",
                              height: "30px",
                              marginRight: "10px",
                            }}
                          />
                          <input
                            type="text"
                            value={newCommentText}
                            onChange={(e) => setNewCommentText(e.target.value)}
                            placeholder="Add a new comment..."
                            style={{
                              border: "none",
                              borderBottom: "1px solid black",
                              flex: "1",
                              fontSize: "14px",
                              backgroundColor: "#F3F3F3",
                              marginLeft: "6px",
                              marginRight: "10px",
                              outline: "none",
                            }}
                          />
                          <button
                            onClick={handleAddComment}
                            style={{
                              fontSize: "16px",
                              borderRadius: "20px",
                              backgroundColor: "#1976D2",
                              color: "white",
                              padding: "10px 20px",
                              cursor: "pointer",
                              border: "none",
                              outline: "none",
                            }}
                          >
                            Add Comment
                          </button>
                        </div>,
                      ]
                    : [
                        <div
                          style={{
                            display: "flex",
                            justifyContent: "space-between",
                            border: "2px solid #B6B6B6",
                            padding: "2%",
                            color: "#B6B6B6",
                            borderRadius: "7px",
                          }}
                        >
                          <span
                            style={{
                              fontWeight: "bold",
                              fontSize: "22px",
                              marginTop: "0.6%",
                            }}
                          >
                            Login or Signup to add a comment
                          </span>
                          <div>
                            <button
                              style={{
                                fontSize: "16px",
                                borderRadius: "22px",
                                backgroundColor: "#1976D2",
                                color: "white",
                                padding: "14px 24px",
                                cursor: "pointer",
                                border: "none",
                                outline: "none",
                                marginRight: "5px"
                              }}
                              onClick={() => {
                                navigate('/login');
                              }}
                            >
                              Login
                            </button>
                            <button
                              style={{
                                fontSize: "16px",
                                borderRadius: "22px",
                                color: "#1976D2",
                                padding: "12px 22px",
                                cursor: "pointer",
                                border: "2px solid #1976D2",
                              }}
                              onClick={() => {
                                navigate('/signup');
                              }}
                            >
                              Signup
                            </button>
                          </div>
                        </div>,
                      ]}
                  <Box
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    height="200px"
                  >
                    <Typography variant="h5" color="textSecondary">
                      There is no comment. Be the first one to comment!
                    </Typography>
                  </Box>
                </>
              ) : (
                <CommentSection comments={commentsData} />
              )}
            </div>
          )}

          {selectedTab === 1 && (
            <div
              style={{
                marginLeft: "0%",
                marginRight: "9.5%",
                marginTop: "0.5%",
                marginBottom: "1%",
                padding: "25px",
                border: "1px solid #ddd",
                borderRadius: "8px",
              }}
            >
              {description}
            </div>
          )}
        </Box>
      </div>
      <Dialog
        open={paymentSuccessModalOpen}
        onClose={handlePaymentSuccessModalClose}
      >
        <DialogTitle>Your Payment has been successful</DialogTitle>
        <DialogContent>
          {/* You can add additional information or components here */}
        </DialogContent>
        <DialogActions>
          <Button onClick={handlePaymentSuccessModalClose}>Close</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default ProductDetails;
