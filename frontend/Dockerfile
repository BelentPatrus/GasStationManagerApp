# Stage 1: Build the Vite app
FROM node:20 AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine

# Copy the Vite output (dist folder) to nginx html dir
COPY --from=build /app/dist /usr/share/nginx/html

# Optional: add custom nginx.conf if you want to support SPA routing
