FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
WORKDIR /src/app
ADD target/book-docker.jar /src/app
ENTRYPOINT [ "java", "-jar", "/src/app/book-docker.jar"] 