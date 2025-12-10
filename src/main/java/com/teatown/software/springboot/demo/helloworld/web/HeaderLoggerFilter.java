package com.teatown.software.springboot.demo.helloworld.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
class HeaderLoggerFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderLoggerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logHeaders(request);
        filterChain.doFilter(request, response);
    }

    private void logHeaders(HttpServletRequest request) {
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            final var values = new ArrayList<String>();
            request.getHeaders(header).asIterator().forEachRemaining(values::add);
            LOGGER.debug("{}: {}", header, values.size() == 1 ? values.getFirst() : values);
        });
    }
}
