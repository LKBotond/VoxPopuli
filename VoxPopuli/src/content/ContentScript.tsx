import { createShadowRoot } from "./shadow";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "../pages/Content";
const shadow  = createShadowRoot();
const container = document.createElement("div");
shadow.appendChild(container);
const root = createRoot(container);
root.render(
  <StrictMode>
    <App />
  </StrictMode>
);