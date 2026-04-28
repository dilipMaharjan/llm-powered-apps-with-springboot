package com.dmed.llm_powered_apps_with_springboot.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryExporterConfig {

    @Bean
    public OtlpGrpcSpanExporter otlGrpcSpanExporter(@Value("${opentelemetry.otel.exporter.otlp.endpoint}") String endpoint) {
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint(endpoint)
                .build();
    }

}
