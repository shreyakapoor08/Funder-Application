import React, { useMemo } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import FormatListNumberedIcon from "@mui/icons-material/FormatListNumbered";

import investmentImage from "../../../Assets/investment-image.png";
import founderImage from "../../../Assets/founder.png";

import "./Sidebar.css";
import {
  Caption1,
  Card,
  CardHeader,
  CardPreview,
  Text,
} from "@fluentui/react-components";

const styles = {
  cardImg: {
    width: "72px",
    height: "56px",
    right: "220px",
    marginLeft: "6px",
    marginTop: "6px",
    borderRadius: "7px",
    padding: "5px",
  },
};

const Sidebar = ({ DASHBOARD_TYPES }) => {
  const dashboardsOptions = useMemo(
    () => [
      {
        type: DASHBOARD_TYPES.INVESTMENT_DASHBOARD,
        path: "/dashboard/investment",
        image: investmentImage,
        title: "Investment",
        key: "Investment",
      },
      {
        type: DASHBOARD_TYPES.FOUNDER_DASHBOARD,
        path: "/dashboard/founder",
        image: founderImage,
        title: "Founder",
        key: "Founder",
      },
    ],
    []
  );
  const navigate = useNavigate();

  const location = useLocation();

  const myCard = (data) => {
    return (
      <Card
        key={data.key}
        appearance="outline"
        className={`dashboard-card shadow2
         ${
           data.path === location.pathname ? "selected-type-card" : "type-card"
         }`}
        compact
        orientation="horizontal"
        onClick={() => {
          navigate(data.path);
        }}
      >
        <CardPreview horizontal>
          <img style={styles.cardImg} src={data.image} alt={data.title} />
        </CardPreview>

        <CardHeader
          header={<Text weight="semibold">{data.title}</Text>}
          description={<Caption1>Dashboard</Caption1>}
        ></CardHeader>
      </Card>
    );
  };

  return (
    <div className="sidebar">
      <div>
        <div className="availbale-report-lable">
          <FormatListNumberedIcon fontSize="small" />
          <span className="title">Available Dashboards</span>
        </div>
        <div className={"dashboard-type-wrapper"}>
          <ul>{dashboardsOptions.map((dashType) => myCard(dashType))}</ul>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
