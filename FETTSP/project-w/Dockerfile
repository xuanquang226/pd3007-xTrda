FROM node:20-alpine
WORKDIR /app
COPY package.json ./
ARG NEXT_PUBLIC_API_URL
ENV NEXT_PUBLIC_API_URL=${NEXT_PUBLIC_API_URL}
COPY . .
RUN npm install
RUN npm run build
CMD ["npm", "start"]