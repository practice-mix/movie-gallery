package com.example.moviegallery.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class User {
    private Integer uid;

    @NotEmpty
    private String username;

    @NotEmpty
    private String phone_number;
}
