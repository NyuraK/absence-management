FROM maven:3.5-jdk-8 AS build  
RUN mvn clean package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8088
RUN mvn clean install
COPY --from=build target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "/app.jar"]
