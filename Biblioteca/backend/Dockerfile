# Etapa 1: Build com Maven (imagem intermediária)
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagem leve para produção
FROM eclipse-temurin:17-jre

ENV TZ=America/Fortaleza
WORKDIR /app

# Pegando qualquer .jar gerado e nomeando como app.jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
