FROM adoptopenjdk/openjdk11
COPY /target/order-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]