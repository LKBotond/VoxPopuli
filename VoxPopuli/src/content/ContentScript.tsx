import { createShadowRoot } from "./shadow";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import tailwindStyles from ".././styles/Tailwind.css?inline";
import App from "../pages/Content";
const shadow = createShadowRoot();
const styleElement = document.createElement("style");
styleElement.textContent = tailwindStyles;
shadow.appendChild(styleElement);
const container = document.createElement("div");
shadow.appendChild(container);
const root = createRoot(container);
root.render(
  <StrictMode>
    <App />
  </StrictMode>,
);
