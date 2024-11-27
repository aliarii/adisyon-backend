# 1. Base Image: Uygulama için gerekli Java sürümünü içerir
FROM openjdk:17-jdk-slim

# 2. Uygulamanın jar dosyasını konteynere kopyala
ARG JAR_FILE=target/adisyon-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 3. Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "/app.jar"]
