import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import { resolve } from "path";

export default defineConfig({
  plugins: [react(), tailwindcss()],
  build: {
    emptyOutDir: false,
    outDir: "dist",
    lib: {
      entry: resolve(__dirname, "src/content/ContentScript.tsx"),
      name: "ContentScript",
      formats: ["iife"],
      fileName: () => "contentScript.js",
    },
    rollupOptions: {
      external: [],
    },
  },
});