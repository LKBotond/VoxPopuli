# commentservice/Dockerfile
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/CommentService-0.0.1-SNAPSHOT.jar app.jar
# Different port from userservice
EXPOSE 8081   
ENTRYPOINT ["java", "-jar", "app.jar"]