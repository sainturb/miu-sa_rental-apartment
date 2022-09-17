FROM adoptopenjdk/openjdk11
COPY /target/paypal-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app.jar"]