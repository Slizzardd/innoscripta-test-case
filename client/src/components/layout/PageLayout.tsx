import React from "react";
import Header from "./Header";

import styles from "./PageLayout.module.scss";

interface PageLayoutProps {
  children: React.ReactNode;
}

const PageLayout: React.FC<PageLayoutProps> = (props) => {
  return (
    <>
      <Header />
      <div className={styles.page}>{props.children}</div>
    </>
  );
};

export default PageLayout;
