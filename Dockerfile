# Etapa 1: Build da aplicação
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR application

# Copia os arquivos necessários para construir o projeto
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Executa o build e gera o JAR
RUN ./mvnw package -Dmaven.test.skip=true

# Extrai as camadas do JAR usando o Spring Boot Layertools
RUN java -Djarmode=layertools -jar target/monitodehabitos-0.0.1-SNAPSHOT.jar extract

# Etapa 2: Imagem final para execução
FROM eclipse-temurin:21-jre-alpine

WORKDIR application

# Copia as camadas extraídas da etapa de build
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

# Define o comando de inicialização da aplicação
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
