# ticket-backend

Spring Boot backend for support ticket intake. This service receives the request, publishes the message to RabbitMQ, and exposes metrics and traces for observability. Email delivery does not happen here: `backend2` consumes the queue and sends the email.

Minimal observability architecture for this project:

- metrics: Spring Boot Actuator + Micrometer -> Prometheus -> Grafana Cloud
- traces: Micrometer Tracing / OTLP -> Grafana Cloud Tempo
- logs: Logback JSON on the console

This project does not use Grafana Alloy, Loki, or Jaeger.

## Environment Variables

Main variables:

- `RABBITMQ_URL`: RabbitMQ connection URL
- `RABBITMQ_QUEUE`: queue used to publish incidents
- `GRAFANA_OTLP_TRACES_ENDPOINT`: Grafana Cloud OTLP HTTP endpoint
- `GRAFANA_OTLP_AUTHORIZATION`: `Authorization` header in the format `Basic <base64(instance_id:token)>`
- `GRAFANA_CLOUD_PROMETHEUS_REMOTE_WRITE_URL`: Grafana Cloud `remote_write` endpoint
- `GRAFANA_CLOUD_PROMETHEUS_USER`: Grafana Cloud Prometheus instance ID
- `GRAFANA_CLOUD_PROMETHEUS_API_KEY`: token with metrics write permission

## Application

Run locally:

```bash
bash ./mvnw spring-boot:run
```

Relevant endpoints:

- API: `POST /send-ticket`
- health: `GET /actuator/health`
- metrics: `GET /actuator/prometheus`
- Swagger: `GET /swagger-ui.html`

## Prometheus

The `prometheus.yml` file already points to the backend `/actuator/prometheus` endpoint and to Grafana Cloud `remote_write` using environment variables.

Run backend + Prometheus:

```bash
docker compose up --build
```

## Grafana Dashboard

Import [ticket-backend-observability-dashboard.json](/home/luhmgba/Desktop/Dev/Java/support incidents/ticket-backend/dashboards/ticket-backend-observability-dashboard.json) into Grafana to get a starter dashboard with:

- service availability
- request rate
- p95 HTTP latency
- p99 HTTP latency
- 4xx rate
- 5xx rate
- request rate by endpoint
- latency by endpoint with p50, p95, and p99
- JVM memory and threads
- Tomcat busy threads
- CPU, GC, and uptime

Public dashboard:

- https://lmgaspa.grafana.net/public-dashboards/37dc483d55ba4abf8bbb0f9419dde5ac

## Observability

- HTTP metrics are exposed automatically by Actuator/Micrometer
- traces are exported directly to Grafana Cloud Tempo via `management.otlp.tracing.*`
- JSON logs include `traceId` and `spanId` for correlation
- the dashboard highlights latency percentiles, error rates, JVM health, and thread pressure
