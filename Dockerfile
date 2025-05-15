FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /app/target/WebApp.jar ./WebApp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "WebApp.jar"]