import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,          // so you can open it from another device if needed
    port: 3001,
    proxy: {
      // all frontend calls to /consumer/* go to your local backend
      "/consumer": {
        target: "http://localhost:8080", // change if your backend runs elsewhere/port
        changeOrigin: true,
      },
    },
  },
});
