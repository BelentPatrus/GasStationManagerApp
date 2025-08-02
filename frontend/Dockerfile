# Stage 1: Build the Vite app
FROM node:20 AS build

WORKDIR /app
ARG VITE_MODE=production
COPY package*.json ./
RUN npm install

COPY . .
# Add this argument to allow mode switching

RUN npm run build -- --mode $VITE_MODE

# Stage 2: Serve with Nginx
FROM nginx:alpine

# Copy the Vite output (dist folder) to nginx html dir
COPY --from=build /app/dist /usr/share/nginx/html

# Optional: add custom nginx.conf if you want to support SPA routing
