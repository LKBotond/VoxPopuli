import { createShadowRoot } from "../../features/comment/shadow";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import tailwindStyles from "../../shared/styles/Tailwind.css?inline";
import ContentApp from "./ContentApp";
const shadow = createShadowRoot();
const styleElement = document.createElement("style");
styleElement.textContent = tailwindStyles;
shadow.appendChild(styleElement);
const container = document.createElement("div");
shadow.appendChild(container);
const root = createRoot(container);
root.render(
  <StrictMode>
    <ContentApp />
  </StrictMode>,
);
