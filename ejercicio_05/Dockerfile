# Etapa 1: Construcción del JAR con Maven
FROM maven:3.9.8-eclipse-temurin-17 AS builder

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el pom.xml y descargar dependencias (para aprovechar la caché)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente
COPY src ./src

# Compilar y empaquetar el proyecto sin ejecutar los tests
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera solo con Java para ejecutar el JAR
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el jar compilado desde la etapa anterior
COPY --from=builder /app/target/ejercicio_05-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de la aplicación
EXPOSE 8080

# Variable de entorno opcional para el puerto
ENV PORT=8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
