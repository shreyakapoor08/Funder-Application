import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Navbar from "../Common/Navbar";
import Footer from "../Common/Footer";
import axios from "axios";
import "./BlogDetails.css";
import config from "../../config";

const BlogDetails = () => {
  const { id } = useParams();
  const [blog, setBlog] = useState({});
  const [dataIsLoaded, setDataIsLoaded] = useState(false);
  const URL = config.URL_PATH;

  useEffect(() => {
    const fetchBlogDetails = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios({
          method: "GET",
          url:
            `${URL}/blog/getBlog/` +
            parseInt(id.toString()),
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const data = response.data;
        setBlog(data);
        setDataIsLoaded(true);
      } catch (error) {
        console.error("Error fetching blog details:", error);
      }
    };

    fetchBlogDetails();
  }, [id]);

  return (
    <div>
      <Navbar />
      {dataIsLoaded ? (
        <div className="container">
          <div className="blog-title">{blog.title}</div>
          <div className="blog-image">
            <img className="img" src={blog.imageName} alt={blog.title} />
          </div>

          <div className="blog-description">
            {blog.content.split("\n").map(
              (line, index) =>
                line.trim() !== "" && (
                  <p className="blog-content" key={index}>
                    {line.trim()}
                  </p>
                )
            )}
          </div>
        </div>
      ) : (
        <div>
          <h1>Loading blog details...</h1>
        </div>
      )}
      <Footer />
    </div>
  );
};

export default BlogDetails;
