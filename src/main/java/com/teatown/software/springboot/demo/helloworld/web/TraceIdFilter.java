package com.teatown.software.springboot.demo.helloworld.web;

import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class TraceIdFilter extends OncePerRequestFilter {

    private final Tracer tracer;

    @Autowired
    public TraceIdFilter(final Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        final String traceId = getTraceId();

        if (traceId != null) {
            log.info("Adding Trace ID {} to HTTP response headers", traceId);
            final var key = "X-Trace-Id";
            log.info("Setting response header {}: {}", key, traceId);
            response.setHeader(key, traceId);
        }

        filterChain.doFilter(request, response);
    }

    private @Nullable String getTraceId() {
        log.info("Getting trace ID from current trace context");
        final TraceContext context = this.tracer.currentTraceContext().context();

        if (context == null) {
            log.warn("Current trace context is null");
            return null;
        }

        return context.traceId();
    }
}
