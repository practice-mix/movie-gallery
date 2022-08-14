package com.example.moviegallery.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://192.168.0.100:9000")
                        .credentials("minioadmin", "minioadmin")
                        .build();

        return minioClient;
    }
}
