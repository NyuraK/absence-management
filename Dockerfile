FROM maven:3.5-jdk-8 AS build
COPY frontend /usr/src/app/frontend
COPY backend /usr/src/app/backend
ADD pom.xml /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean install

FROM openjdk:8-jre-alpine
EXPOSE 8088
COPY --from=build /usr/src/app/backend/target/backend-0.0.1-SNAPSHOT.jar /usr/app/backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "/app.jar"]