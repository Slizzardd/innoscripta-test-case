import React from "react";
import { News } from "../store/newsStore.module";

import styles from "./NewsItem.module.scss";

interface NewsItemProps {
  item: News;
}

const NewsItem: React.FC<NewsItemProps> = (props) => {
  const news = props.item;

  return (
    <div className={styles.news}>
      <h3>{news.title}</h3>

      <div className={styles.news_info}>
        {news.sourceImage !== null && (
          <img
            src={news.sourceImage}
            alt={news.title}
            className={styles.news_info_image}
          />
        )}
        {news.description !== null && (
          <p className={styles.news_info_description}>{news.description}</p>
        )}


        {news.content !== null && <p>content: {news.content}</p>}

        <h3>{news.publisher}</h3>
      </div>

      <a href={news.sourcePublisher} className={styles.news_source}>
        source
      </a>
    </div>
  );
};

export default NewsItem;
