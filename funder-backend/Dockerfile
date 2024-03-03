FROM maven:3.8.3-openjdk-17 AS build
COPY . /home/app
WORKDIR /home/app
RUN mvn -f /home/app/pom.xml clean install

FROM openjdk:17-jdk-slim
COPY --from=build /home/app/target/funder-backend-0.0.1-SNAPSHOT.jar /app/funder-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/funder-backend.jar"]
