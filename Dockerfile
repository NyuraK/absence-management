FROM openjdk:8-jre-alpine
EXPOSE 8088
ADD backend/target/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=Europe/London","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "/app.jar"]
