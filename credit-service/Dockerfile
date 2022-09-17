FROM adoptopenjdk/openjdk11
COPY /target/credit-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/app.jar"]