import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { PAGES, ElementByPage } from "./pages.enum";

const router = createBrowserRouter([
  { path: PAGES.Home, element: ElementByPage[PAGES.Home], index: true },
  { path: PAGES.Auth, element: ElementByPage[PAGES.Auth] },
  { path: PAGES.Profile, element: ElementByPage[PAGES.Profile] },
]);

const Router = () => {
  return <RouterProvider router={router} />;
};

export default Router;
