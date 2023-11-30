import Home from "../pages/Home";
import React from "react";
import Auth from "../pages/Auth";
import Profile from "../pages/Profile";

enum PAGES {
  "Home" = "/",
  "Auth" = "/auth",
  "Profile" = "/profile",
}

const ElementByPage: { [keys in PAGES]: React.ReactNode } = {
  [PAGES.Home]: <Home />,
  [PAGES.Auth]: <Auth />,
  [PAGES.Profile]: <Profile />,
};

export { PAGES, ElementByPage };
