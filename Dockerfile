FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/manejo_clientes-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_clientes.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app_clientes.jar"]
