FROM openjdk:21-jdk-slim

WORKDIR /app

ADD ./opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

COPY build/libs/app-0.1.0.jar /app/hello-world-api.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=development

ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:4317

ENV OTEL_RESOURCE_ATTRIBUTES="service.name=hello-world-api"

ENTRYPOINT ["sh", "-c", "java -javaagent:/app/opentelemetry-javaagent.jar -jar /app/hello-world-api.jar --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
