# --- GIAI ĐOẠN 1: BUILD ---
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


# --- GIAI ĐOẠN 2: RUN ---
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 80

ENTRYPOINT ["java","-jar","app.jar","--server.port=80","--server.address=0.0.0.0"]