import React from "react";
import { Link } from "react-router-dom";
import { PAGES } from "../../router/pages.enum";
import { rootStore } from "../../store/rootStore";

import styles from "./Header.module.scss";

const Header = () => {
  const { token, user } = rootStore.authStore((store) => ({
    token: store.authToken,
    user: store.user,
  }));

  return (
    <header className={styles.header}>
      <div className={styles.header_wrapper}>
        <ul className={styles.header_nav}>
          <li className={styles.header_nav_item}>
            <Link to={PAGES.Home}>Home</Link>
          </li>
        </ul>

        {token === null ? (
          <Link to={PAGES.Auth} className={styles.header_profileLink}>
            Sign in
          </Link>
        ) : (
          <Link to={PAGES.Profile} className={styles.header_profileLink}>
            {user?.firstName} {user?.lastName}
          </Link>
        )}
      </div>
    </header>
  );
};

export default Header;
