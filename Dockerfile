FROM openjdk:16-alpine3.13
VOLUME /tmp
COPY target/ProductService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]