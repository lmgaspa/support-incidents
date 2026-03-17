# 💬 Support Incidents

A lightweight microservices-based support incident platform built to keep ticket handling fast, clear, and reliable.

The idea is simple: when someone asks for help, support should feel effortless.

A user opens a ticket on the website, the system records the request, publishes it to RabbitMQ, and the support team receives everything by email in a clean and organized flow.

---

## ✨ Overview

This project follows a lightweight microservices architecture where each service has a clear responsibility:

- **Frontend (Vue)**: collects support incidents from users
- **ticket-backend**: receives incidents and publishes them to RabbitMQ
- **email-backend**: consumes the queue, sends emails, and stores delivery records in MongoDB

This separation keeps the flow simple, scalable, and easy to maintain.

---

## 🧩 Architecture

The system is built with **independent microservices** communicating asynchronously through **RabbitMQ**.

### 🛠 Tech Stack

- **Java 17**
- **Spring Boot**
- **Vue**
- **RabbitMQ**
- **MongoDB**
- **Spring Boot Actuator**
- **Micrometer**
- **Prometheus**
- **Grafana Cloud**
- **OpenTelemetry**

---

## 🧱 How It Works

1. The user opens a support incident on the **Vue frontend**
2. The **ticket-backend** receives the request
3. The incident is published to **RabbitMQ** on the `incident_queue`
4. The **email-backend** consumes the message
5. The email is sent to the support team
6. The delivery record is stored in **MongoDB**

---

# 🎫 ticket-backend

The `ticket-backend` is the entry point for support incident intake.

It is responsible for:

- receiving the support request
- publishing the incident to RabbitMQ
- exposing metrics and traces for observability

> Email delivery does **not** happen here.  
> `email-backend` is responsible for consuming the queue and sending the email.

---

## 🔭 Observability

This service includes a minimal and practical observability setup.

### Metrics
- **Spring Boot Actuator**
- **Micrometer**
- **Prometheus**
- **Grafana Cloud**

### Traces
- **Micrometer Tracing**
- **OTLP**
- **Grafana Cloud Tempo**

### Logs
- **Logback JSON logs** on the console
- correlation with `traceId` and `spanId`

### Not Used
This project does **not** use:

- Grafana Alloy
- Loki
- Jaeger

---

## 🔐 Environment Variables

Main environment variables used by the service:

- `RABBITMQ_URL` — RabbitMQ connection URL
- `RABBITMQ_QUEUE` — queue used to publish incidents
- `GRAFANA_OTLP_TRACES_ENDPOINT` — Grafana Cloud OTLP HTTP endpoint
- `GRAFANA_OTLP_AUTHORIZATION` — `Authorization` header in the format `Basic <base64(instance_id:token)>`
- `GRAFANA_CLOUD_PROMETHEUS_REMOTE_WRITE_URL` — Grafana Cloud `remote_write` endpoint
- `GRAFANA_CLOUD_PROMETHEUS_USER` — Grafana Cloud Prometheus instance ID
- `GRAFANA_CLOUD_PROMETHEUS_API_KEY` — token with permission to write metrics

---

## ▶️ Running the Application

Run locally with:

```bash
./mvnw spring-boot:run
