package com.belentpatrus.gasstation.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestId = Optional.ofNullable(request.getHeader("X-Request-ID"))
                .filter(s -> !s.isBlank())
                .orElse(UUID.randomUUID().toString());
        MDC.put("requestId", requestId.substring(0, 8));

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String path = (query == null) ? uri : uri + "?" + query;

        log.info("Incoming {} {}", method, path);       // before controller runs
        long start = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        }catch (Throwable t) {
            log.error("Failed {} {} rid={}", method, path, requestId, t);
            throw t;
        }finally {
            long tookMs = System.currentTimeMillis() - start;
            log.info("Completed {} {} -> {} ({} ms)", method, path, response.getStatus(), tookMs, requestId);
            MDC.clear();
        }

    }
}
