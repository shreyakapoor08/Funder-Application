import React, { useState } from "react";

import { Panel } from "@fluentui/react";
import { Button } from "@fluentui/react-components";
import CloseIcon from "@mui/icons-material/Close";
import { Checkbox } from "antd";

import allCategories from "./categoriesConstant";
import { useDashboardContext } from "./DashboardContext";

import MUIButton from "@mui/material/Button";

import "./CategoryFilter.css";

const CategoryFilter = ({ isSidePanelOpen, setIsSidePanelOpen }) => {
  const { selectedFilter, setSelectedFilter } = useDashboardContext();
  const [tempSelectedCategory, setTempSelectedCategory] = useState([]);

  const categoryChangeHandler = ({ isSelected, category }) => {
    setTempSelectedCategory((prev) =>
      isSelected
        ? [...prev, category.title]
        : prev.filter((item) => item !== category.title)
    );
  };

  const onSaveHandler = () => {
    setSelectedFilter((prev) => ({
      ...prev,
      categories: tempSelectedCategory.map((title) => ({ title })),
    }));
    setIsSidePanelOpen(false);
    setTempSelectedCategory([]);
  };

  const onClearFilterHandler = () => {
    setTempSelectedCategory([]);
    setSelectedFilter((prev) => ({
      ...prev,
      categories: [],
    }));
  };

  const onOpenHandler = () => {
    setTempSelectedCategory(selectedFilter.categories.map((category) => category.title));
  };

  return (
    <Panel
    isOpen={isSidePanelOpen}
    onDismiss={() => {
      setIsSidePanelOpen(false);
      setTempSelectedCategory([]);
    }}
    closeButtonAriaLabel="Close"
    hasCloseButton={false}
    onOpen={onOpenHandler}
  >
      <div className="category-filter-wrapper">
        <div className="header">
          <p className="title">Category Filter</p>
          <Button
            icon={<CloseIcon />}
            onClick={() => {
              setIsSidePanelOpen(false);
              setTempSelectedCategory([]);
            }}
          ></Button>
        </div>
        <div className="category-list-wrapper">
          {allCategories.map((item) => (
            <div key={item.title} className="category-wrapper">
              <Checkbox
                onChange={({ target }) =>
                  categoryChangeHandler({
                    isSelected: target?.checked,
                    category: item,
                  })
                }
                checked={tempSelectedCategory.includes(item.title)}
              >
                {item.title}
              </Checkbox>
            </div>
          ))}
        </div>

        <div className="category-action-btn-wrapper">
          <MUIButton
            variant="outlined"
            size="small"
            onClick={onClearFilterHandler}
          >
            Clear
          </MUIButton>
          <MUIButton variant="contained" size="small" onClick={onSaveHandler}>
            Save
          </MUIButton>
        </div>
      </div>
    </Panel>
  );
};

export default CategoryFilter;
