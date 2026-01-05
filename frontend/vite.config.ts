import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";


export default defineConfig({
  plugins: [react()],
   resolve: {
    alias: {
      "@": new URL("./src", import.meta.url).pathname,
    },
  },
  server: {
    host: true,          // so you can open it from another device if needed
    port: 5173,
    proxy: {
      "/api" : {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
