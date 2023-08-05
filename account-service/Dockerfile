FROM maven:3.8.1-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

##
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/quarkus-app/ /app/
EXPOSE 8080
CMD ["java", "-jar", "/app/quarkus-run.jar"]
