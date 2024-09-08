# Hello World API

The **Hello World API** is a simple Spring Boot application designed to demonstrate logging capabilities using the `logging-starter` library. This API logs incoming HTTP requests with various custom headers, providing a consistent and centralized logging approach for microservices.

## Features

- **Consistent Logging**: Automatically captures and logs requests and responses with predefined MDC (Mapped Diagnostic Context) fields.
- **Environment Profiles**: Supports different Spring profiles (development, production) for environment-specific configurations.
- **Dockerized Setup**: Includes Docker and Docker Compose setup for running and testing the API in isolated environments.
- **API Simulation**: Uses a side container to simulate API calls with different headers to illustrate logging in action.

## Prerequisites

- Java 21
- Docker and Docker Compose
- Taskfile (Task)

## Getting Started

### Building and Running the Project

You can use the provided `Taskfile.yml` to build and run the project easily. Here's a breakdown of the tasks:

1. **Build the Project**

   To build the project using Gradle:

   ```bash
   task build
    ```
1. **Build the Docker Image**
   To build the Docker image for the API:

   ```bash
   task docker-build   
   ```

1. **Run the Docker Image**
   To run the Docker image with a specific Spring profile (default is development):

   ```bash
   task docker-run   
   ```

1. **Run with Docker Compose**
   To start the API along with a simulated client sending requests every 3 seconds, use:

   ```bash
    task compose-up SPRING_PROFILES_ACTIVE=production
   ```


## Logging Configuration
By default, the hello-world-api uses the logging configuration provided by the logging-starter library. The default `log4j2-spring.xml` can be customized by modifying your application's `application.yml` file.

## How to Use as a Dependency
To use `logging-starter` in your projects, include it as a dependency in your `build.gradle`:

```gradle
dependencies {
    implementation 'com.example:logging-starter:1.0.0'
}
```

## Example API Call Logs
Run the Docker Compose setup to observe logs in action. The logs will show incoming requests to the Hello World API with headers and other details, demonstrating the centralized logging provided by the `logging-starter` library.