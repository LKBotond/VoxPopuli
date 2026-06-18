import { createShadowRoot } from "./content/services/ShadowDomService";
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import tailwindStyles from "@/entrypoints/common/styles/Tailwind.css?inline";
import ContentApp from "./content/ContentApp";

export default defineContentScript({
  matches: ["<all_urls>"],

  main(ctx) {
    console.log("CONTENT SCRIPT LOADED");
    const shadow = createShadowRoot();
     console.log("VoxPopuli content script loaded");

    const styleElement = document.createElement("style");
    styleElement.textContent = tailwindStyles;
    shadow.appendChild(styleElement);

    const container = document.createElement("div");
    shadow.appendChild(container);

    createRoot(container).render(
      <StrictMode>
        <ContentApp />
      </StrictMode>
    );
  },
});

