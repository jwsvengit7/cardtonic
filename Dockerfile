FROM openjdk:17
WORKDIR /app
COPY target/cardmonix-app-0.0.1-SNAPSHOT.jar ./cardmonix-app.jar
EXPOSE 8085
CMD ["java", "-jar", "cardmonix-app.jar"]
