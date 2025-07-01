FROM openjdk:17-jdk-slim
WORKDIR /app
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*
COPY target/clinica-medica-administrativo-1.0-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 8080
ENTRYPOINT ["/wait-for-it.sh", "mysql-administrativo", "3306", "--", "java", "-jar", "/app/app.jar"]
