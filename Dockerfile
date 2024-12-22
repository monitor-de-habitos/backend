FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR application
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
# Extrai as camadas do JAR usando o Spring Boot Layertools
RUN java -Djarmode=layertools -jar target/monitodehabitos-0.0.1-SNAPSHOT.jar extract

# Etapa 2: Imagem final para execução
FROM eclipse-temurin:21-jre-alpine

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
