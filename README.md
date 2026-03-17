# 💬 Support Incidents

A microservices-based support incident platform designed for reliable incident intake, asynchronous processing, traceability, and operational visibility.

The platform handles the full support flow from ticket submission to email delivery through decoupled services connected by RabbitMQ. It also provides observability with metrics, traces, and structured logs, along with automated validation through tests and CI/CD pipelines.

---

## ✨ Overview

The system is composed of independent services, each with a single responsibility:

- **Frontend (Vue):** collects support incidents from users
- **ticket-backend:** receives incident requests and publishes messages to RabbitMQ
- **email-backend:** consumes messages, sends support emails, and persists both **ticket data** and **email delivery records** in MongoDB

This design keeps the workflow loosely coupled, easier to evolve, and more resilient than a synchronous end-to-end flow.

---

## 🧩 Architecture

The platform follows an event-driven microservices architecture.

### 🧱 Core Flow

1. A user submits a support incident through the **Vue frontend**
2. The **ticket-backend** validates and receives the request
3. The incident is published to RabbitMQ on the `incident_queue`
4. The **email-backend** consumes the message asynchronously
5. The email is sent to the support team
6. Both **ticket data** and **email delivery records** are persisted in **MongoDB**

This architecture separates intake from delivery, reduces coupling between services, and improves maintainability and operational clarity.

---

## 🛠 Technology Stack

### Backend
- **Java 17**
- **Spring Boot**

### Frontend
- **Vue**

### Messaging
- **RabbitMQ**

### Persistence
- **MongoDB**

### Observability
- **Spring Boot Actuator**
- **Micrometer**
- **Prometheus**
- **Grafana Cloud**
- **OpenTelemetry**

### Quality Engineering
- **JUnit**
- **Mockito**
- **GitHub Actions**

---

## ✅ Engineering Practices

Both backend services were developed with emphasis on code quality, maintainability, and delivery confidence.

### Automated Testing
- unit tests implemented with **JUnit**
- dependency isolation and mocking with **Mockito**
- test coverage applied across both backends

### CI/CD
- **GitHub Actions** configured for both backend services
- automated build and test execution on push and pull request
- continuous integration pipelines to improve delivery confidence and prevent regressions

These practices help keep the services production-oriented and easier to evolve over time.

---

## 🎫 ticket-backend

The `ticket-backend` is responsible for the incident intake layer.

Its responsibilities include:

- receiving support incident requests
- validating incoming payloads
- publishing messages to RabbitMQ
- exposing metrics and traces for operational monitoring

It does **not** send emails or persist final operational records.

Those responsibilities belong to the `email-backend`, which consumes the queue, performs email delivery, and persists both **ticket data** and **email records** in MongoDB.

---

## 📡 Main Endpoints

- `POST /send-ticket` — submits a new support incident
- `GET /actuator/health` — application health endpoint
- `GET /actuator/prometheus` — Prometheus metrics endpoint
- `GET /swagger-ui.html` — Swagger UI

---

## 🔭 Observability

The platform includes an observability layer designed to improve runtime visibility, incident diagnostics, and operational confidence.

### Metrics

Application metrics are exposed through **Spring Boot Actuator** and **Micrometer**, then scraped by **Prometheus** and visualized in **Grafana Cloud**.

The metrics layer focuses on operational signals such as:

- service availability
- request throughput
- latency percentiles
- error rate distribution
- JVM health
- thread utilization
- CPU and garbage collection behavior

### Distributed Tracing

Tracing is instrumented with **Micrometer Tracing** and exported through **OTLP** to **Grafana Cloud Tempo**.

This allows the request path to be followed across the service lifecycle, improving the ability to investigate latency, processing bottlenecks, and failure scenarios.

### Structured Logging

Application logs are emitted in structured JSON format through **Logback**.

Each log entry is enriched with:

- `traceId`
- `spanId`

This enables correlation between logs and traces, making troubleshooting more precise and improving operational traceability.

### 🌍 Public Observability Dashboard

The project also includes a public Grafana Cloud dashboard for live observability visualization:

[View Public Grafana Cloud Dashboard](https://lmgaspa.grafana.net/public-dashboards/37dc483d55ba4abf8bbb0f9419dde5ac)

---

## 📊 Dashboard Coverage

The dashboard provides visibility into key runtime and application-level indicators, including:

- service availability
- request throughput
- p95 HTTP latency
- p99 HTTP latency
- 4xx error rate
- 5xx error rate
- request rate by endpoint
- latency by endpoint with p50, p95, and p99
- JVM memory consumption
- JVM thread activity
- Tomcat busy threads
- CPU usage
- garbage collection activity
- application uptime

---

## 🗄 Persistence Model

Operational data is persisted in **MongoDB** to preserve traceability throughout the support workflow.

Persisted records include:

- ticket payloads
- email delivery records
- processing history associated with support operations

This persistence strategy improves:

- auditability
- troubleshooting
- historical analysis
- operational transparency

---

## 🚀 Architectural Characteristics

This project was designed around a set of practical engineering decisions:

- **asynchronous communication** through RabbitMQ to decouple intake from email delivery
- **clear service boundaries** to keep each backend focused on a single responsibility
- **observability-first backend design** to improve diagnostics and operational insight
- **persistent operational records** in MongoDB to support traceability and auditing
- **automated validation** through tests and CI/CD to improve delivery reliability

Together, these choices produce a platform that is easier to evolve, easier to monitor, and more resilient than a tightly coupled synchronous workflow.

---

## ⭐ Why This Project Stands Out

This repository demonstrates practical experience with:

- event-driven microservices architecture
- asynchronous backend processing
- ticket and email record persistence in MongoDB
- service boundary design and separation of concerns
- automated testing with JUnit and Mockito
- CI/CD pipelines with GitHub Actions
- observability with metrics, traces, and structured logs
- production-style monitoring with Grafana Cloud

---

## 👤 Author

**Luiz Gasparetto**

- Website: [andescoresoftware.com.br](https://andescoresoftware.com.br)
- Email: [andescoresoftware@gmail.com](mailto:andescoresoftware@gmail.com)
