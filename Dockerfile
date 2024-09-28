FROM openjdk:21-jdk

WORKDIR /app

COPY . /app

RUN ./mvnw package

ENTRYPOINT ["java", "-jar", "target/your-app.jar"]
