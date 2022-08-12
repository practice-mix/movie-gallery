package com.example.moviegallery.dao.entity;

import lombok.Data;

@Data
public class User {
    private Integer uid;

    private String nickname;

    private String avatar;

    private String phone_number;
}