FROM adoptopenjdk/openjdk11
COPY /target/payment-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app.jar"]