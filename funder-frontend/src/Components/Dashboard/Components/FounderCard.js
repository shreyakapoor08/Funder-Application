import {
  Button,
  Tooltip,
  Tag as FluentUITag,
} from "@fluentui/react-components";
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";
import { Typography } from "@mui/material";
import React, { useState } from "react";
import styled from "styled-components";
import moment from "moment/moment";
import PercentIcon from "@mui/icons-material/Percent";
import LocalAtmIcon from "@mui/icons-material/LocalAtm";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import Chip from "@mui/material/Chip";
import axios from "axios";
import config from "../../../config";

const Section = styled.div`
  width: 100%;
  background: #ffffff;
  box-shadow: 0px 0.3px 0.9px rgba(0, 0, 0, 0.07),
    0px 1.6px 3.6px rgba(0, 0, 0, 0.11);
  border-radius: 8px;
  position: relative;
  margin-right: 20px;
  margin-bottom: 20px;
  .second {
    padding: 10px 10px 10px 10px;
  }
`;

const FirstSection = styled.div`
  height: 170px;
  display: flex;
  flex-direction: row;
  align-items: start;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;

  video {
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    height: 100%;
    width: 100%;
    object-fit: fill;
  }

  img {
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    height: 100%;
    width: 100%;
    object-fit: fill;
  }
`;

const FounderActions = styled.div`
  margin-top: 10px;
  display: flex;
  justify-content: end;
`;

const CategoryWrapper = styled.div`
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-bottom: 8px;
`;

const Ellipsis = styled.span`
  display: inline-block;
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const Title = styled.div`
  font-weight: 700;
  font-size: 19px;
  line-height: 20px;
  color: #242424;
  flex: none;
  order: 0;
  flex-grow: 0;
  cursor: pointer;
  height: 38px;
  overflow: hidden;
  word-break: break-word;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  -webkit-box-orient: vertical;

  @media screen and (max-width: 768px) {
    font-size: 16px;
  }
`;

const FounderInfo = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0;
  margin: 10px 0;

  .icon {
    width: 23px;
    height: 23px;
  }

  .text {
    font-weight: 500;
    font-size: 17px;
    line-height: 23px;
    color: #424242;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    flex-grow: 1;
    flex-shrink: 1;
    min-width: 0;
  }

  @media screen and (max-width: 768px) {
    .text {
      font-size: 14px;
    }
  }
`;

const Description = styled(Typography)`
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-bottom: 8px;
`;

const FounderCard = ({ data, onEdit, onDelete }) => {
  const currentDate = moment();
  const isFundingClosed =
    data.dateOfRoundClosing &&
    moment(data.dateOfRoundClosing).isBefore(currentDate);

  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const URL = config.URL_PATH;

  const handleDelete = async () => {
    try {
      const accessToken = localStorage.getItem("token");
      // const userId = localStorage.getItem("id");
      const apiUrl = `${URL}/products/delete/${data.id}`;

      const response = await axios.delete(apiUrl, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });

      onDelete(data.id);

      setOpenDeleteDialog(false);
    } catch (error) {
      console.error("Error deleting product:", error);
    }
  };

  return (
    <Section>
      <FirstSection>
        <img src={data.productImage} alt={data.title} />
      </FirstSection>

      <div className="second">
        <CategoryWrapper>
          {data.category.map((map_category, index) => (
            <Ellipsis key={index}>
              <Chip
                label={map_category.title}
                className="category-chip"
                style={{
                  marginRight: "5px",
                  marginBottom: "5px",
                }}
                variant="outlined"
                color="primary"
              />
            </Ellipsis>
          ))}
        </CategoryWrapper>

        <div>
          <Title>
            <Tooltip positioning={"below"} content={data.title}>
              <span> {data.title}</span>
            </Tooltip>
          </Title>
          <Description variant="body2" color="textSecondary" gutterBottom>
            {data.description}
          </Description>
          <FounderInfo>
            <span className="text">
              <span>
                {data.dateOfRoundClosing &&
                moment(data.dateOfRoundClosing).isValid()
                  ? "Round Closing Date: "
                  : "NA"}
              </span>
              {data.dateOfRoundClosing &&
              moment(data.dateOfRoundClosing).isValid()
                ? moment(data.dateOfRoundClosing).utc().format("DD, MMM YYYY")
                : ""}
            </span>
          </FounderInfo>
          <FounderInfo>
            <PercentIcon className="icon" />
            <span className="text">{data.percentage} Percentage Dilution</span>
          </FounderInfo>
          <FounderInfo>
            <LocalAtmIcon className="icon" />
            <span className="text">
              {data.raisedAmount} / {data.fundingAmount}
            </span>
          </FounderInfo>
        </div>
        <FounderActions>
          {isFundingClosed && (
            <FluentUITag
              style={{
                marginRight: "10px",
                padding: "5px",
                background: "#FF5722",
                color: "white",
                borderRadius: "4px",
              }}
            >
              Funding Closed
            </FluentUITag>
          )}
          <Button
            appearance="transparent"
            onClick={() => onEdit(data)}
            icon={<EditIcon />}
          />
          <Button
            appearance="transparent"
            onClick={() => setOpenDeleteDialog(true)}
            icon={<DeleteIcon />}
          />
          <Dialog
            open={openDeleteDialog}
            onClose={() => setOpenDeleteDialog(false)}
          >
            <DialogTitle>Delete Confirmation</DialogTitle>
            <DialogContent>
              <Typography variant="body1">
                Are you sure you want to delete this item?
              </Typography>
            </DialogContent>
            <DialogActions>
              <Button onClick={() => setOpenDeleteDialog(false)}>Cancel</Button>
              <Button onClick={handleDelete} color="error">
                Delete
              </Button>
            </DialogActions>
          </Dialog>
        </FounderActions>
      </div>
    </Section>
  );
};

export default FounderCard;
