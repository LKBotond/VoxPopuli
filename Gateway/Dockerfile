FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY Gateway/pom.xml .
COPY contracts/target/contracts-1.0.jar /tmp/contracts-1.0.jar
RUN mvn install:install-file \
    -Dfile=/tmp/contracts-1.0.jar \
    -DgroupId=com.VoxPopuli \
    -DartifactId=contracts \
    -Dversion=1.0 \
    -Dpackaging=jar

RUN mvn dependency:go-offline -B
COPY Gateway/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]