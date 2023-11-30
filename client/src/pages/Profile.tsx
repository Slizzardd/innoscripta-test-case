import React, { useEffect, useState } from "react";
import { rootStore } from "../store/rootStore";
import { useNavigate } from "react-router-dom";
import { PAGES } from "../router/pages.enum";
import PageLayout from "../components/layout/PageLayout";

import styles from "./Profile.module.scss";

const Profile = () => {
  const authStore = rootStore.authStore();
  const user = authStore.user;
  const token = authStore.authToken;

  const [loadingPublishers, setLoadingPublishers] = useState(false);
  const [publishers, setPublishers] = useState<
    Array<{ active: boolean; name: string }>
  >(
    authStore.publishers.map((name) => ({
      active: user === null ? false : user.preferredPublishers.includes(name),
      name,
    })),
  );

  const navigate = useNavigate();

  const logoutHandler = () => {
    authStore.logout();
    navigate(PAGES.Home);
  };
  const deleteHandler = () => {
    if (token !== null) {
      authStore.deleteUser(token).then(() => {
        navigate(PAGES.Home);
      });
    } else {
      navigate(PAGES.Home);
    }
  };

  useEffect(() => {
    if (token === null) {
      navigate(PAGES.Home);
    } else {
      authStore.getUser(token).then(res => {
        if(res === null){
          authStore.logout();
          navigate(PAGES.Home)
        }
      });
    }

    setLoadingPublishers(true);
    authStore.getPublishers().then((pubs) => {
      setLoadingPublishers(false);
      if (pubs !== null) {
        setPublishers(
          pubs.map((name) => ({
            active:
              user === null ? false : user.preferredPublishers.includes(name),
            name,
          })),
        );
      }
    });
  }, []);

  useEffect(() => {
    setPublishers((prev) =>
      prev.map((item) => ({
        active:
          authStore.user === null
            ? false
            : authStore.user?.preferredPublishers.includes(item.name),
        name: item.name,
      })),
    );
  }, [authStore.user?.preferredPublishers]);

  const pubChangeClick = (name: string) => {
    setPublishers((prev) => {
      const newPubs = prev.map((item) =>
        item.name === name ? { active: !item.active, name } : item,
      );

      authStore.changeUser({
        ...user,
        preferredPublishers: newPubs
          .filter((item) => item.active)
          .map((item) => item.name),
      });

      return newPubs;
    });
  };

  return (
    <PageLayout>
      <div className={styles.profilePage}>
        <section className={styles.profilePage_leftSection}>
          <h1>Profile</h1>

          <p className={styles.profilePage_leftSection_line}>
            FirstName: {user?.firstName}
          </p>
          <p className={styles.profilePage_leftSection_line}>
            LastName: {user?.lastName}
          </p>

          <p className={styles.profilePage_leftSection_line}>
            Email: {user?.email}
          </p>

          <div>
            <p>preferredPublishers: </p>
            <ul>
              {user?.preferredPublishers.map((pub, index) => (
                <li key={index}>{pub}</li>
              ))}
            </ul>
          </div>

          <button onClick={logoutHandler}>logout</button>
          <button onClick={deleteHandler}>delete</button>
        </section>

        <section className={styles.profilePage_rightSection}>
          <h2>Change Profile</h2>
          {loadingPublishers ? (
            "loading publishers..."
          ) : (
            <>
              {publishers.map((pub, index) => (
                <div key={index}>
                  <p>{pub.name}</p>
                  <input
                    type={"checkbox"}
                    checked={pub.active}
                    onChange={() => pubChangeClick(pub.name)}
                  />
                </div>
              ))}
            </>
          )}
        </section>
      </div>
    </PageLayout>
  );
};

export default Profile;
