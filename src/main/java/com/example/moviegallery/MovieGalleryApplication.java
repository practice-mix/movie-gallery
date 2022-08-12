package com.example.moviegallery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.moviegallery.dao"})
@EnableTransactionManagement
public class MovieGalleryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieGalleryApplication.class, args);
    }

}
