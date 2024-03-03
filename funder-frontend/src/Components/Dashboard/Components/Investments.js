import React, { useEffect, useState } from "react";
import moment from "moment";
import axios from "axios";
import InvestmentCard from "./InvestmentCard";
import { useDashboardContext } from "./DashboardContext";
import "./Investments.css";
import config from "../../../config";

// const baseUrl = process.env.REACT_APP_URL_PATH;
// const token = `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJia2I2NDcwODdAZ21haWwuY29tIiwiaWF0IjoxNjk4NTU5NDY3LCJleHAiOjE2OTg5MTk0Njd9.v6r_VIitdeGjxV0yVGvqipt_DAzGvgVTDWYpxupHKYw`;

const Investments = () => {
  const [allInvestments, setAllInvestments] = useState([]);
  const [filteredInvestments, setFilteredInvestments] = useState([]);
  // const [isLoadingData, setIsLoadingData] = useState(false);
  const URL = config.URL_PATH;

  const { selectedFilter } = useDashboardContext();

  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("id");

  const fetchInvestments = async () => {
    // setIsLoadingData(true);
    try {
      const { data } = await axios({
        method: "GET",
        url: `${URL}/investedProduct/getAllInvestedProduct`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: { userId: userId },
      });

      if (data?.length) {
        let formattedData = data.map((item) => {
          const {
            id,
            moneyInvested,
            percentageOwned,
            dateOfInvestment,
            product: {
              fundingAmount,
              title,
              productImage,
              category,
              raisedAmount,
            },
          } = item;
          return {
            id,
            categories: category ? [category] : [],
            title,
            dateOfInvestment,
            moneyInvested,
            PercentageOwned: percentageOwned,
            totalIvestmentGot: raisedAmount,
            totalInvestmentSeeking: fundingAmount,
            productImage,
          };
        });
        setAllInvestments(formattedData);
      } else {
        setAllInvestments([]);
      }
    } catch (error) {
    } finally {
      // setIsLoadingData(false);
    }
  };

  useEffect(() => {
    fetchInvestments();
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    let allData = [...allInvestments];
    allData = allData.map((item) => {
      const parsedCategories = JSON.parse(item.categories[0]).map(
        (category) => ({ title: category.title })
      );
      return {
        ...item,
        categories: parsedCategories,
      };
    });
    if (selectedFilter.startDate) {
      allData = allData.filter(
        ({ dateOfInvestment }) =>
          moment(selectedFilter.startDate) <= moment(dateOfInvestment)
      );
    }
    if (selectedFilter.endDate) {
      allData = allData.filter(
        ({ dateOfInvestment }) =>
          moment(selectedFilter.endDate) >= moment(dateOfInvestment)
      );
    }
    if (selectedFilter.categories?.length) {
      allData = allData.filter(({ categories }) =>
        categories.some((founderCategory) =>
          selectedFilter.categories.some(
            (filterCategory) => founderCategory.title === filterCategory.title
          )
        )
      );
    }
    if (selectedFilter.textSearch?.replace(/\s/g, "")?.length) {
      allData = allData.filter(({ title }) =>
        title
          ?.replace(/\s/g, "")
          ?.toLowerCase()
          ?.includes(selectedFilter.textSearch.replace(/\s/g, "").toLowerCase())
      );
    }
    setFilteredInvestments(allData);
  }, [allInvestments, selectedFilter]);

  return (
    <div className="investments-page-wrapper">
      {filteredInvestments.length ? (
        <div className="investment-cards">
          {filteredInvestments.map((item) => (
            <div className="investment-card-wrapper" key={item.id}>
              <InvestmentCard data={item} />
            </div>
          ))}
        </div>
      ) : allInvestments.length ? (
        <div className="no-data-text">
          No Investment data available for this filter
        </div>
      ) : (
        <div className="no-data-text">No Investment data available</div>
      )}
    </div>
  );
};

export default Investments;
