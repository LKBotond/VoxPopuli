import { defineConfig } from "vite";
import path from "node:path";
import { fileURLToPath } from "node:url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default defineConfig({
  build: {
    rollupOptions: {
      input: {
        popup: path.resolve(__dirname, "src/ui/html/Index.html"),
        background: path.resolve(__dirname, "src/background.ts"),
        //content: path.resolve(__dirname, "src/cotentScripts/Content.ts"),
      },
    },
    outDir: "dist",
    emptyOutDir: true,
  },
});
