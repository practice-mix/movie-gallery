package com.example.moviegallery.dao.model;


import lombok.Data;

@Data
public class UserLocationProjection {
    private Integer uid;

    private String username;
    private String phone_number;

    private Float latitude;
    private Float longitude;
    private Float distance;
}
