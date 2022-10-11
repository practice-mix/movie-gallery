package com.example.moviegallery.dao.entity;

import lombok.Data;

@Data
public class Error {
    private Integer id;

    private String message;

    private String stacktrace;
}