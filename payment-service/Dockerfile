FROM openjdk:17
VOLUME /tmp
COPY target/payment-service-0.0.1-SNAPSHOT.jar payment-service.jar
ENTRYPOINT ["java","-jar","/payment-service.jar"]
