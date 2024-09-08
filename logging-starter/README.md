# Logging Starter

A logging library for Java Spring Boot applications that addresses cross-cutting concerns such as consistent logging, MDC (Mapped Diagnostic Context) support, and centralized configuration. The `logging-starter` library provides a standardized way to handle logging across multiple microservices, ensuring uniformity in log formats and content.

## Why Use Logging Starter?

`logging-starter` provides:

- **Consistent Logging**: Ensures that all microservices use a standardized logging format and configuration.
- **MDC (Mapped Diagnostic Context) Support**: Automatically captures important fields like `X-Trace-Token`, `X-Correlation-Id`, and custom headers configurable via application context.
- **Flexible Configuration**: Allows centralized configuration through `log4j2-spring.xml` and overrides via `application.yml`.
- **Cross-Cutting Concerns Handling**:
    - Global exception handling for standardized error responses.
    - Request and response logging for HTTP calls.
    - Customizable log levels for different environments (development, production).

## Features

1. **Global Exception Handling**: The `GlobalExceptionHandler` class handles all uncaught exceptions in a standardized way, providing meaningful error messages and logs.

2. **Logging Interceptor**: The `LoggingInterceptor` class intercepts incoming HTTP requests, extracts important headers, and populates them into the MDC (Mapped Diagnostic Context) for consistent logging.

3. **Request and Response Logging**: The `RequestResponseLoggingFilter` class logs the incoming requests and outgoing responses, allowing developers to trace requests through the system.

4. **Centralized Configuration**:
    - `log4j2-spring.xml`: Provides default log configurations for different environments (development and production). This file is loaded by Spring Boot automatically.
    - `application.yml`: Allows customization of log patterns, levels, and additional fields to capture.

## How to Build

### Prerequisites

Ensure that you have the following installed:

- Java 17 or higher
- Gradle
- Docker (if using Docker for builds)

### Build Instructions

To build the project, you can use the included Taskfile commands:

1. **Clean and Build the Project**:

    ```bash
    task clean_build
    ```

2. **Publish Locally**:

    ```bash
    task publish_local
    ```

This command will clean, build, and publish the project to your local Maven repository.

## How to Use as a Dependency

### Add to Your Project

Include the following dependency in your Spring Boot application's `build.gradle` file:

```gradle
dependencies {
    implementation 'com.example:logging-starter:0.1.0'
}
