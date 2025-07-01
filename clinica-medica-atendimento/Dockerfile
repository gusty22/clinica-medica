FROM openjdk:17-jdk-slim
WORKDIR /app
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*
COPY target/clinica-medica-atendimento-1.0-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 8082
ENTRYPOINT ["/wait-for-it.sh", "mysql-atendimento", "3306", "--", "java", "-jar", "/app/app.jar"]
