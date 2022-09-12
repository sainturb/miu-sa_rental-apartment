# STAGE ONE - BUILD PACKAGE
FROM maven:3.6.0-jdk-11-slim AS MAVEN_BUILD
COPY ./ ./
RUN mvn package -l log.txt -DskipTests=true

# STAGE TWO - MOUNT JAR
FROM openjdk:17.0.1-jdk-slim
COPY --from=MAVEN_BUILD /target/order-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]