# Spring Boot demo

This is a demo Spring Boot application that ...
1. exposes simple RESTful APIs
2. is instrumented with OpenTelemetry for logs, tracing and metrics
3. sends telemetry data to a local Grafana Alloy (OpenTelemetry collector) via the OTLP protocol

---

## OpenTelemetry configuration

To export telemetry data, you can either leverage the accompanied OpenTelemetry Java agent, or use the OpenTelemetry Java SDK and instrument your code. 

In general, as of Spring Boot v4, a Spring Boot application exports metrics to an OpenTelemetry collector without any additional configuration.
In fact, you need to actively disable metrics exporting by setting `management.otlp.metrics.export.enabled=false` if you don't want to export metrics to an OpenTelemetry collector.

In contrast, to export logs and traces, you need to at least define the OpenTelemetry collector endpoint (e.g, in application.yaml) where the telemetry data should be exported to.

### Metrics - Exporting via Java agent

No additional configuration is needed to export metrics via the OpenTelemetry Java agent. 
Just run your application with `-javaagent:path/to/opentelemetry-javaagent.jar` and the agent will automatically instrument your application and export metrics to the configured OpenTelemetry collector endpoint.

Note: 
If your application uses Spring Boot v4 or later, you need to actively disable metrics exporting by setting `management.otlp.metrics.export.enabled=false` if you don't want metrics to be exported via the OpenTelemetry SDK. 
However, if you use the OpenTelemetry Java agent, metrics will be exported by default without any additional configuration.

### Metrics - Exporting via SDK

As stated above, if your application uses Spring Boot v4 or later, exporting metrics to an OpenTelemetry collector is enabled by default and works out of the box without any additional configuration.

### Logs - Exporting via Java agent

No additional configuration is needed to export logs via the OpenTelemetry Java agent. 
Just run your application with `-javaagent:path/to/opentelemetry-javaagent.jar` and the agent will automatically instrument your application and export logs to the configured OpenTelemetry collector endpoint.

### Logs - Exporting via SDK

For exporting logs via the OpenTelemetry SDK, you need to add some additional configuration to your application.

1. install the OpenTelemetry API to the `io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender`
   1. OpenTelemetry API: `io.opentelemetry.api.OpenTelemetry`
   2. see `com.teatown.software.springboot.demo.application.opentelemetry.InstallOpenTelemetryAppender`
2. add an appender to your logback configuration that uses the `io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender` class
   1. see `src/main/resources/logback-spring.xml` for an example configuration

For further details on how the local Grafana observability stack is setup please refer to the [observability-stack/README.md](./observability-stack/README.md) file.