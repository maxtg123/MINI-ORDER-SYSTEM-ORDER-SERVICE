FROM openjdk:17
VOLUME /tmp
COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service.jar
ENTRYPOINT ["java","-jar","/inventory-service.jar"]


#FROM maven:3.8.5-openjdk-17 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/inventory-service-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8082
#ENTRYPOINT ["java", "-jar", "app.jar"]
