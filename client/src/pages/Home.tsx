import React, { useEffect, useState } from "react";
import PageLayout from "../components/layout/PageLayout";
import { rootStore } from "../store/rootStore";
import Filter, { I_Filter } from "../components/Filter";
import NewsItem from "../components/NewsItem";
import styles from "./Home.module.scss";

let timer: NodeJS.Timeout | null;

function debounce<T extends (...args: any[]) => void>(callback: T, ms: number) {
  return function (...args: Parameters<T>) {
    if (timer) {
      clearTimeout(timer);
    }

    timer = setTimeout(() => {
      callback(...args);
    }, ms);
  };
}

const Home = () => {
  const [_page, setPage] = useState<number>(0);

  const [disableLoadMore, setDisableLoadMore] = useState(false);

  const newsStore = rootStore.newsStore();
  const authStore = rootStore.authStore();

  const [filter, setFilter] = useState<I_Filter>({
    sortBy: "title",
    orderBy: "desc",
    specifications: "",
    myFavorite: false,
  });

  useEffect(() => {
    newsStore.clearNews();
    setPage(0);

    debounce(loadMoreHandler, 800)();
  }, [filter]);



  function loadMoreHandler() {
    setPage((prev) => {
      const newPage = prev + 1;

      if (!disableLoadMore) {
        setDisableLoadMore(true);
        newsStore
          .getNews({ ...filter, page: newPage - 1, authToken: authStore.authToken })
          .then((fetched) => {

            if (fetched === null){
              authStore.logout();
            }

            setDisableLoadMore(false);
          });
        setDisableLoadMore(false);
      }

      return newPage;
    });
  }

  return (
    <PageLayout>
      <Filter filter={filter} setFilter={setFilter} />

      <div className={styles.newsList}>
        {newsStore.news.length === 0
          ? "loading news..."
          : newsStore.news.map((news, index) => (
              <NewsItem item={news} key={index} />
            ))}
      </div>

      {newsStore.endNews ? (
        <div>News end</div>
      ) : (
        <button onClick={loadMoreHandler}>load more news</button>
      )}
    </PageLayout>
  );
};

export default Home;
