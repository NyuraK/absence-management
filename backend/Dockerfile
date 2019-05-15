FROM openjdk:8-jre-alpine
VOLUME /tmp
EXPOSE 8088
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=UTC", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "/app.jar"]