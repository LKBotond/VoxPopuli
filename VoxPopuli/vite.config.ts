import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import webExtension from "vite-plugin-web-extension";
const isCI = process.env.CI === "true" || !!process.env.CI;
const isWatch = process.argv.includes("--watch");

export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
    webExtension({
      manifest: "manifest.json",
      additionalInputs: ["src/entries/interior.html"],
      disableAutoLaunch: isCI||isWatch,
    }),
  ],
  build: {
    watch: isCI ? null : {},
  },
});
