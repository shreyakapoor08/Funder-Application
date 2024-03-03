import pdfMake from "pdfmake/build/pdfmake";
import pdfFonts from "pdfmake/build/vfs_fonts";
import moment from "moment";

import companyImage from "../../Assets/logo2.png";
import signatureImage from "../../Assets/signature.png";

pdfMake.vfs = pdfFonts.pdfMake.vfs;
pdfMake.fonts = {
  Roboto: {
    normal: "Roboto-Regular.ttf",
    bold: "Roboto-Medium.ttf",
    italics: "Roboto-Italic.ttf",
    bolditalics: "Roboto-Italic.ttf",
  },
};
function readImage(url) {
  return new Promise((resolve) => {
    var request = new XMLHttpRequest();
    request.onload = function () {
      var file = new FileReader();
      file.onloadend = function () {
        resolve(file.result);
      };
      file.readAsDataURL(request.response);
    };
    request.open("GET", url);
    request.responseType = "blob";
    request.send();
  });
}

export const pdfGenerator = async (data) => {
  const {
    id,
    video,
    categories,
    title,
    dateOfInvestment,
    moneyInvested,
    PercentageOwned,
    backers,
    totalIvestmentGot,
    totalInvestmentSeeking,
    user = "user",
  } = data;

  const logoImage = await readImage(companyImage);
  const signImage = await readImage(signatureImage);
  const firstName = localStorage.getItem("firstName");
  var dd = {
    content: [
      {
        layout: "noBorders",
        table: {
          widths: ["*", "*"],

          body: [
            [
              {
                columns: [
                  {
                    image: "logoImage",
                    width: 150,
                  },
                ],
              },
              dateOfInvestment && moment(dateOfInvestment).isValid()
                ? {
                    text: `Investment Date: ${moment(dateOfInvestment).format(
                      "DD, MMM YYYY"
                    )}`,
                    fontSize: 11,
                    margin: [0, 15, 0, 0],
                    alignment: "right",
                  }
                : "",
            ],
          ],
        },
      },
      {
        text: [
          {
            text: "This Certifies that ",
            color: "#416187",
            bold: true,
            fontSize: 15,
          },
          {
            text: `  ${firstName}  `,
            fontSize: 18,
            decoration: "underline",
            bold: true,
          },
          " is the registered holder of ",
          {
            text: `  ${PercentageOwned}  `,
            fontSize: 18,
            decoration: "underline",
            lineHeight: 1.2,
          },
          "% shares worth $",
          {
            text: `  ${moneyInvested}  `,
            fontSize: 18,
            decoration: "underline",
            lineHeight: 1.2,
          },
          " in ",
          {
            text: `  ${title}  `,
            fontSize: 18,
            decoration: "underline",
            lineHeight: 1.2,
          },
          " transferable only on the books of the Corporation by the holder hereof in person or by Attorney upon surrender of this Certificate property endorsed. ",
          {
            text: " In Witness Whereof ",
            color: "#416187",
            bold: true,
            fontSize: 15,
          },
          " the said Corporation has caused this Certificate to be signed by its duly authorized officers and its Corporate Seal to be hereunto affixed",
        ],
        margin: [0, 70, 0, 0],
        fontSize: 15,
        alignment: "center",
        italics: true,
        lineHeight: 1.5,
      },
      dateOfInvestment && moment(dateOfInvestment).isValid()
        ? {
            text: [
              " this ",
              {
                text: `  ${moment(dateOfInvestment).format("DD")}  `,
                fontSize: 18,
                decoration: "underline",
                lineHeight: 1.2,
              },
              " day of ",
              {
                text: `  ${moment(dateOfInvestment).format("MMMM")}  `,
                fontSize: 18,
                decoration: "underline",
                lineHeight: 1.2,
              },
              " A.D. ",
              {
                text: `  ${moment(dateOfInvestment).year()}  `,
                fontSize: 18,
                decoration: "underline",
                lineHeight: 1.2,
              },
            ],
            fontSize: 15,
            alignment: "center",
            italics: true,
            lineHeight: 1.5,
          }
        : "",

      {
        layout: "noBorders",
        table: {
          widths: ["*", "*", "*", "*"],

          body: [
            [
              {
                columns: [],
              },
              {
                columns: [],
              },
              {
                columns: [],
              },
              {
                columns: [
                  {
                    image: "signImage",
                    width: 70,
                  },
                ],
                margin: [0, 50, 0, 0],
              },
            ],
          ],
        },
      },
    ],
    images: {
      logoImage,
      signImage,
    },
  };

  pdfMake.createPdf(dd).print();
};
