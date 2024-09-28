FROM openjdk:21-jdk

WORKDIR /app

COPY . /app

RUN ./mvnw package

ENTRYPOINT ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
