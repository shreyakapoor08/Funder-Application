import { Button, Tooltip } from "@fluentui/react-components";
import React from "react";
import styled from "styled-components";

import { Tag } from "antd";
import moment from "moment/moment";

import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import PercentIcon from "@mui/icons-material/Percent";
import LocalAtmIcon from "@mui/icons-material/LocalAtm";
import SaveAltIcon from "@mui/icons-material/SaveAlt";
import { pdfGenerator } from "../generatePDF.js";

const Section = styled.div`
  width: 100%;
  background: #ffffff;
  box-shadow: 0px 0.3px 0.9px rgba(0, 0, 0, 0.07),
    0px 1.6px 3.6px rgba(0, 0, 0, 0.11);
  border-radius: 8px;
  position: relative;
  .second {
    padding: 20px 20px 10px 20px;
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

const InvestmentActions = styled.div`
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
`;

const PDFWrapper = styled.div``;

const CategoryWrapper = styled.div`
  display: flex;
  align-items: center;

  .categories {
    display: flex;
    flex-wrap: wrap;
    row-gap: 5px;
  }
`;

const Title = styled.div`
  font-weight: 700;
  font-size: 19px;
  line-height: 20px;
  color: #242424;
  flex: none;
  order: 0;
  flex-grow: 0;
  margin: 8px 0px;
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

const InvestmentInfo = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0px;
  margin: 10px 0;

  .icon {
    position: static;
    width: 23px;
    height: 23px;
  }

  .text {
    font-weight: 500;
    font-size: 17px;
    line-height: 23px;
    color: #424242;
    flex: none;
    order: 1;
    flex-grow: 0;
    margin: 0px 9px;
    // for overflow text
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 175px;
  }
  @media screen and (max-width: 768px) {
    .text {
      font-size: 14px;
    }
  }
`;

const InvestmentCard = ({ data }) => {
  // const downloadPDFHandler = () => {
  //   window.open(data.pdf);
  // };
  const categories = data.categories;
  return (
    <Section>
      <FirstSection>
        <img src={data.productImage} alt={data.title} />
        {/* <video src={data.video} controls /> */}
      </FirstSection>

      <div className="second">
        <CategoryWrapper>
          {categories ? (
            <div className="categories">
              {categories.map((category, index) => (
                <Tooltip
                  key={index}
                  positioning={"below"}
                  content={category.title}
                >
                  <Tag
                    style={{
                      padding: "1px 7px",
                      color: "white",
                      backgroundColor: "#5E17EB",
                      borderRadius: "6px",
                      marginRight: "5px",
                    }}
                    checked
                  >
                    {categories.length > 2
                      ? category.title?.length > 6
                        ? `${category.title.substring(0, 6)}...`
                        : category.title
                      : category.title?.length > 9
                      ? `${category.title.substring(0, 9)}...`
                      : category.title}
                  </Tag>
                </Tooltip>
              ))}
              {!!categories.slice(4).length ? (
                <Tooltip
                  positioning={"below"}
                  content={categories.slice(4).join(", ")}
                >
                  <Tag
                    style={{
                      padding: "1px 1px",
                      borderRadius: "6px",
                      marginRight: "3px",
                    }}
                    checked
                  >
                    {`+${categories.slice(4).length}`}
                  </Tag>
                </Tooltip>
              ) : (
                <div></div>
              )}
            </div>
          ) : (
            <div style={{ height: "20px" }}></div>
          )}
        </CategoryWrapper>
        <div>
          <Title>
            <Tooltip positioning={"below"} content={data.title}>
              <span> {data.title}</span>
            </Tooltip>
          </Title>
          {/* {data.dateOfInvestment && moment(data.dateOfInvestment).isValid() ? ( */}
          <InvestmentInfo>
            <CalendarMonthIcon className="icon" />
            <span className="text">
              {data.dateOfInvestment && moment(data.dateOfInvestment).isValid()
                ? moment(data.dateOfInvestment).format("DD, MMM YYYY")
                : "NA"}
            </span>
          </InvestmentInfo>
          {/* ) : null} */}
          <InvestmentInfo>
            <AttachMoneyIcon className="icon" />
            <span className="text">{data.moneyInvested} Invested</span>
          </InvestmentInfo>
          <InvestmentInfo>
            <PercentIcon className="icon" />
            <Tooltip content={`${data.PercentageOwned} Percentage Own`}>
              <span className="text">
                {data.PercentageOwned} Percentage Own
              </span>
            </Tooltip>
          </InvestmentInfo>
          {/* <InvestmentInfo>
            <PeopleAltIcon className="icon" />
            <span className="text">{data.backers} Backers</span>
          </InvestmentInfo>*/}

          <InvestmentInfo>
            <LocalAtmIcon className="icon" />
            <span className="text">
              {data.totalIvestmentGot ?? 0} / {data.totalInvestmentSeeking}
            </span>
          </InvestmentInfo>
        </div>
        <InvestmentActions>
          <PDFWrapper>
            <Tooltip content="Download PDF" relationship="label">
              <Button
                appearance="transparent"
                onClick={() => pdfGenerator(data)}
                icon={<SaveAltIcon />}
              />
            </Tooltip>
          </PDFWrapper>
        </InvestmentActions>
      </div>
    </Section>
  );
};

export default InvestmentCard;
