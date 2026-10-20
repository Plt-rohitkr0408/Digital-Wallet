FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 9090

COPY target/DigitalWallet-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java" , "-jar", "app.jar"]