FROM node:16-alpine
WORKDIR /usr/src/app/funder-frontend
ENV REACT_APP_BACKEND_HOST = "http://localhost:8080"
COPY package.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
