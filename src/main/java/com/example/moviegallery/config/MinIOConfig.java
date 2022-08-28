package com.example.moviegallery.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {

    public static final String MINIO_SERVER_URL_BASE = "https://socialme.hopto.org/";

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(MINIO_SERVER_URL_BASE)
                        .credentials("minioadmin", "minioadmin")
                        .build();

        return minioClient;
    }
}
