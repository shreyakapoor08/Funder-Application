import React from "react";
import { Route, Routes } from "react-router-dom";
import Navbar from "../Common/Navbar";
import Sidebar from "./Components/Sidebar";
import DashboardContext from "./Components/DashboardContext";
import Investments from "./Components/Investments";
import Founder from "./Components/Founder"
import Controls from "./Components/Controls"

import "./Dashboard.css";

const DASHBOARD_TYPES = {
  INVESTMENT_DASHBOARD: "INVESTMENT_DASHBOARD",
  FOUNDER_DASHBOARD: "FOUNDER_DASHBOARD",
};

const Dashboard = () => {
  return (
    <DashboardContext>
      <div className="dashboard">
        <Navbar />
        <div>
          <Controls />
          <div className="main-wrapper">
            <Sidebar DASHBOARD_TYPES={DASHBOARD_TYPES} />
            <div className="page-wrapper">
              <Routes>
                <Route path="investment" element={<Investments />} />
                <Route path="founder" element={<Founder />} />
              </Routes>
            </div>
          </div>
        </div>
      </div>
    </DashboardContext>
  );
};

export default Dashboard;
