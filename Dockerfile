#
# Build stage
#
# Stage 1: Build the application
FROM maven:4.0-openjdk-20 AS build
WORKDIR /home/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:20
COPY --from=build /home/app/target/mlb_api-0.0.1-SNAPSHOT.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
