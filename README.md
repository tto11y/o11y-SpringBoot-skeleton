# Spring Boot demo

This is a demo Spring Boot application that ...
1. exposes simple RESTful APIs
2. is instrumented with OpenTelemetry for logs, tracing and metrics
3. sends telemetry data to a local Grafana Alloy (OpenTelemetry collector) via the OTLP protocol 

For further details on how the local Grafana observability stack is setup please refer to the [observability-stack/README.md](../observability-stack/README.md) file.