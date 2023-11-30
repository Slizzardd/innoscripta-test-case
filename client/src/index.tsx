import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import "./styles/index.scss";

const root = document.getElementById("root") as HTMLElement;

createRoot(root).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
