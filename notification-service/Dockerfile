FROM adoptopenjdk/openjdk11
COPY /target/notification-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app.jar"]