FROM adoptopenjdk/openjdk11
COPY /target/product-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/app.jar"]