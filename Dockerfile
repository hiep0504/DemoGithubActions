# --- GIAI ĐOẠN 1: BUILD ---
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy source code
COPY . .

# Build file jar
RUN mvn clean package -DskipTests


# --- GIAI ĐOẠN 2: RUN ---
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy file jar từ stage builder
COPY --from=builder /app/target/*.jar app.jar

# Mở port
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java","-jar","app.jar"]