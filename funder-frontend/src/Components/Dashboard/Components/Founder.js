import React, { useEffect, useState } from "react";
import moment from "moment";
import FounderCard from "./FounderCard";
import { useDashboardContext } from "./DashboardContext";
import "./Founders.css";
import AddFounderForm from "./addFounderForm";
import { Button } from "@mui/material";
import axios from "axios";
import config from "../../../config";

const Founder = () => {
  const userid = localStorage.getItem("id");
  const URL = config.URL_PATH;
  const [allFounders, setAllFounders] = useState([]);
  const [filteredFounders, setFilteredFounders] = useState([]);
  const { selectedFilter } = useDashboardContext();

  const [isFormOpen, setIsFormOpen] = useState(false);

  const [isEditMode, setIsEditMode] = useState(false);

  const [editFormData, setEditFormData] = useState({
    title: "",
    description: "",
    dateOfRoundClosing: "",
    fundingAmount: "",
    percentage: "",
    productImage: null,
    category: [],
    user: { id: userid },
  });

  // const addFounder = (newFounder) => {
  //   setAllFounders((prevFounders) => [...prevFounders, newFounder]);
  // };

  const editFounder = (founder) => {
    setEditFormData(founder);
    setIsFormOpen(true);
    setIsEditMode(true);
  };

  const deleteFounder = (founderId) => {
    const updatedFounders = allFounders.filter(
      (founder) => founder.id !== founderId
    );
    setAllFounders(updatedFounders);
    setFilteredFounders(updatedFounders); // Also update filteredFounders if needed
  };

  useEffect(() => {
    const accessToken = localStorage.getItem("token");
    const userId = localStorage.getItem("id");
    axios
      .get(`${URL}/products/user/${userId}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((response) => {
        const updatedData =
          response.data &&
          response.data.map((element) => {
            return {
              ...element,
              category: JSON.parse(element.category),
            };
          });
        setAllFounders(updatedData);
        setFilteredFounders(updatedData);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  useEffect(() => {
    let allData = [...allFounders];
    if (selectedFilter.startDate) {
      allData = allData.filter(
        ({ dateOfRoundClosing }) =>
          moment(selectedFilter.startDate) <= moment(dateOfRoundClosing)
      );
    }
    if (selectedFilter.endDate) {
      allData = allData.filter(
        ({ dateOfRoundClosing }) =>
          moment(selectedFilter.endDate) >= moment(dateOfRoundClosing)
      );
    }
    if (selectedFilter.categories?.length) {
      allData = allData.filter(({ category }) =>
        category.some((founderCategory) =>
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
    setFilteredFounders(allData);
  }, [selectedFilter]);

  return (
    <div className="investments-page-wrapper">
      <Button
        variant="contained"
        color="primary"
        style={{ width: "55%", marginLeft: "5%" }}
        onClick={() => {
          setIsFormOpen(true);
          setIsEditMode(false);
        }}
      >
        Add more products
      </Button>

      <div style={{ marginTop: "20px" }}>
        {filteredFounders.length ? (
          <div className="investment-cards">
            {filteredFounders.map((item) => (
              <div className="investment-card-wrapper" key={item.id}>
                <FounderCard
                  data={item}
                  onEdit={editFounder}
                  onDelete={deleteFounder}
                />
              </div>
            ))}
          </div>
        ) : allFounders.length ? (
          <div className="no-data-text">
            No Founder data available for this filter
          </div>
        ) : (
          <div className="no-data-text">No Founder data available</div>
        )}
      </div>

      <AddFounderForm
        open={isFormOpen}
        onClose={() => {
          setIsFormOpen(false);
          setIsEditMode(false);
        }}
        onSave={(founderData) => {
          founderData.category = JSON.parse(founderData.category);
          if (isEditMode) {
            const updatedFounders = allFounders.map((founder) =>
              founder.id === editFormData.id ? founderData : founder
            );
            setAllFounders(updatedFounders);
            setFilteredFounders(updatedFounders);
          } else {
            setAllFounders([...allFounders, founderData]);
            setFilteredFounders([...filteredFounders, founderData]);
          }

          setIsFormOpen(false);
          setIsEditMode(false);
        }}
        initialFormData={isEditMode ? editFormData : null}
      />
    </div>
  );
};

export default Founder;
