import React, { createContext, useContext, useState } from "react";

const dashboardContext = createContext();

export const useDashboardContext = () => {
  return useContext(dashboardContext);
};

const DashboardContext = ({ children }) => {
  const [selectedFilter, setSelectedFilter] = useState({
    startDate: null,
    endDate: null,
    categories: [],
    textSearch: "",
  });
  return (
    <dashboardContext.Provider value={{ selectedFilter, setSelectedFilter }}>
      {children}
    </dashboardContext.Provider>
  );
};

export default DashboardContext;
