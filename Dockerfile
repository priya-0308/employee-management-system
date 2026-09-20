FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/employee-management-system-0.0.1-SNAPSHOT.jar"]
