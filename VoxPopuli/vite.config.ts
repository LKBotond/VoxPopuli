import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import webExtension from "vite-plugin-web-extension";
export default defineConfig({
  plugins: [
    react(), 
    tailwindcss(),
   webExtension({
      manifest: "manifest.json", 
      additionalInputs: [
        "interior.html"
      ], 
    }),
  ],
});
//export default defineConfig({
//plugins: [react(), tailwindcss()],
//base: "",
//build: {
//    watch: {},
//    outDir: "dist",
//    rollupOptions: {
//      input: {
//        popupIndex: resolve(__dirname, "index.html"),
//        popupInterior: resolve(__dirname, "interior.html"),
//        background: resolve(__dirname, "src/background.ts"),
//      },
//      output: {
//        entryFileNames: "[name].js",
//        chunkFileNames: "chunks/[name].js",
//        assetFileNames: "assets/[name].[ext]",
//      },
//    },
//  },
//});
