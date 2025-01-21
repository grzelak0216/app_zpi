# Etap 1: Budowanie aplikacji
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app

# Kopiowanie plików projektu
COPY pom.xml .
COPY src ./src

# Kopiowanie zasobów statycznych
COPY src/main/resources/static/ /app/resources/static/

# Budowanie aplikacji
RUN mvn clean package -DskipTests

# Etap 2: Uruchamianie aplikacji
FROM openjdk:17-slim
WORKDIR /app

# Skopiowanie skompilowanego pliku JAR z etapu budowania
COPY --from=builder /app/target/*.jar app.jar

# Skopiowanie zasobów statycznych do kontenera
COPY --from=builder /app/resources/static /app/resources/static

# Ekspozycja portu aplikacji
EXPOSE 8081

# Uruchomienie aplikacji
CMD ["java", "-jar", "app.jar"]
