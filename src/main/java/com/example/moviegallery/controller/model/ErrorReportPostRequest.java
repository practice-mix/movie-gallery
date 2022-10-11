package com.example.moviegallery.controller.model;

import lombok.Data;

@Data
public class ErrorReportPostRequest {
    private String message;
    private String stacktrace;
}
