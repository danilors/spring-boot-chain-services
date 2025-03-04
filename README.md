# Spring Boot WebFlux vs. Blocking: Performance Comparison

This repository contains a project designed to evaluate the performance differences between a Spring Boot application using a reactive approach with WebFlux (`workflow-processor`) and a traditional blocking approach (`workflow`). The project aims to demonstrate the benefits and trade-offs of each approach under load.

## Project Structure

The project is composed of the following modules:

*   **`workflow-processor`:** This module represents a service built with Spring Boot WebFlux, leveraging reactive programming to handle requests asynchronously.
*   **`workflow`:** This module represents a service built with traditional Spring Boot, using a blocking I/O model.
*   **`profile`:** A supporting microservice that simulates fetching profile information. It is used by both `workflow-processor` and `workflow`.
*   **`address`:** A supporting microservice that simulates fetching address information. It is used by both `workflow-processor` and `workflow`.
*   **`occupation`:** A supporting microservice that simulates fetching occupation information. It is used by both `workflow-processor` and `workflow`.
*   **`rules`:** A supporting microservice that simulates applying business rules. It is used by both `workflow-processor` and `workflow`.

**Interaction:**

The `profile`, `address`, `occupation`, and `rules` microservices act as dependencies for both `workflow-processor` and `workflow`. This setup is intended to simulate a real-world scenario where multiple microservices interact to fulfill a single request. By leveraging these services, we can see how each `workflow` implementation interacts with external systems.

**Concurrency:**

In both `workflow-processor` and `workflow`, parallel requests are made to the dependent microservices (`profile`, `address`, `occupation`, `rules`). This was implemented to verify the behavior of the applications in scenarios that require the use of concurrency and to evaluate how each approach handles simultaneous requests.

**In-Memory Database:**

The `profile`, `address`, `rules`, and `occupation` projects integrate with an in-memory H2 database through Spring Data. The use of H2 is solely intended to provide data quickly and with an easy setup, avoiding the need for an external database to run the project.

**Redis Integration:**

In the `workflow` project, a simple integration with Redis has been implemented. The purpose of this integration is merely to verify the functionality of Redis, demonstrating its ability to be used with a non-reactive service.

## Project Goal

The primary goal of this project is to compare the performance of `workflow-processor` (reactive) and `workflow` (blocking) under load. We aim to understand:

*   **Latency:** How quickly each application can respond to requests.
*   **Throughput:** How many requests each application can handle per second.
*   **Resource Utilization:** How efficiently each application uses system resources.
*   **Error Rates:** How reliable each application is under load.

## Technology Stack

*   **Java 21:** The core programming language.
*   **Spring Boot:** The framework used for building the applications.
*   **Spring WebFlux:** The reactive web framework used in `workflow-processor`.
*   **Spring Data:** Framework for facilitating database integration.
*   **H2 Database:** In-memory database used by the microservices.
*   **Redis:** Database used in the workflow project.
*   **GraalVM:** Used as the JVM, potentially offering performance improvements.
*   **Apache JMeter:** Load testing tool.

## Load Testing

To evaluate the performance of each service, we created a load testing script using Apache JMeter. The script simulates a specific workload and measures various performance metrics.

### JMeter Script

The JMeter script can be found in the `jmeter` directory.

### Test Scenario

The test scenario typically involves:

*   **Number of Users:** A configurable number of concurrent users.
*   **Ramp-up Period:** The time taken to reach the maximum number of users.
*   **Requests:** A sequence of HTTP requests to each API.

### Detailed Results

Detailed results from one of the load tests are available in the `jmeter/Teste de Carga.md` file. This file contains a comprehensive breakdown of the performance metrics for each API, including:

*   Average response time
*   Median response time
*   Percentiles (90th, 95th, 99th)
*   Error rates
*   Throughput
*   Data transfer rates

## Running the Project

To run the project, you need to execute the commands below in each microservice folder:

```bash
mvn clean spring-boot:run
```