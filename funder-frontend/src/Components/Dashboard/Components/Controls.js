import React, { useState } from "react";
import FilterListIcon from "@mui/icons-material/FilterList";
import { DatePicker } from "@fluentui/react-datepicker-compat";
import { Badge, Field, Input, makeStyles } from "@fluentui/react-components";
import moment from "moment";

import { useDashboardContext } from "./DashboardContext";
import CategoryFilter from "./CategoryFilter";

import "./Controls.css";

const useStyles = makeStyles({
  control: {
    maxWidth: "300px",
  },
});

const Controls = () => {
  const [isSidePanelOpen, setIsSidePanelOpen] = useState(false);

  const { selectedFilter, setSelectedFilter } = useDashboardContext();

  const styles = useStyles();

  return (
    <>
      <CategoryFilter {...{ isSidePanelOpen, setIsSidePanelOpen }} />
      <div className="dashboard-controls">
        <div className="left-controls">
          <Field label="Starting date" className="s_text">
            <DatePicker
              appearance="underline"
              className={styles.control}
              placeholder="Select a date..."
              value={
                selectedFilter.startDate &&
                moment(selectedFilter.startDate).isValid()
                  ? new Date(selectedFilter.startDate)
                  : null
              }
              onSelectDate={(date) => {
                setSelectedFilter((prev) => ({ ...prev, startDate: date }));
              }}
              allowTextInput
            />
          </Field>
          <Field label="Ending date">
            <DatePicker
              appearance="underline"
              className={styles.control}
              placeholder="Select a date..."
              value={
                selectedFilter.endDate &&
                moment(selectedFilter.endDate).isValid()
                  ? new Date(selectedFilter.endDate)
                  : null
              }
              onSelectDate={(date) => {
                setSelectedFilter((prev) => ({ ...prev, endDate: date }));
              }}
              allowTextInput
            />
          </Field>
        </div>
        <div className="right-controls">
          <div className="search-wrapper">
            <Input
              placeholder="Search..."
              appearance="underline"
              value={selectedFilter.textSearch}
              onChange={(e, { value }) => {
                setSelectedFilter((prev) => ({ ...prev, textSearch: value }));
              }}
            />
          </div>

          <div
            className="filter-wrapper"
            onClick={() => {
              setIsSidePanelOpen(true);
            }}
          >
            <div className="filter-icon-counter">
              {selectedFilter?.categories?.length > 0 && (
                <Badge appearance="filled" size="small" className="counter">
                  {selectedFilter.categories.length}
                </Badge>
              )}
              <FilterListIcon />
            </div>
            <p style={{ margin: 0, padding: 5 }}>{"Filter"}</p>
          </div>
        </div>
      </div>
    </>
  );
};

export default Controls;
