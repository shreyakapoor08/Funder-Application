import React, { useState, useEffect } from "react";
import Navbar from "../Common/Navbar";
import Footer from "../Common/Footer";
import { Link } from "react-router-dom";
import "./Blog.css";
import axios from "axios";
import config from "../../config";

const Blog = () => {
  const [blogs, setBlogs] = useState([]);
  const apiURL = config.URL_PATH;

  useEffect(() => {
    const token = localStorage.getItem("token");
    const fetchBlogs = async () => {
      try {
        const { data } = await axios({
          method: "GET",
          url: `${apiURL}/blog/getAllBlogs`,
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setBlogs(data);
      } catch (error) {
        console.error("Error fetching blogs:", error);
      }
    };

    fetchBlogs();
  }, []);

  return (
    <div>
      <Navbar />
      <div className="align_h">
        <p className="b_txt">Blogs</p>
        <div className="t1">
          {blogs.map((post) => (
            <div className="blog_align">
              <div class="card2 ">
                <Link
                  to={`/blog/getBlog/${post.blog_id}`}
                  style={{ textDecoration: "none" }}
                >
                  {" "}
                  {}
                  <div class="card-header">
                    <img src={post.imageName} alt="" />
                  </div>
                  <div class="card-body">
                    <span class="tag tag-teal">{post.blogType}</span>
                    <h4 className="txt_title">{post.title}</h4>
                    <p className="txt_body">{post.shortDescription}</p>
                  </div>
                </Link>
              </div>
            </div>
          ))}
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default Blog;
