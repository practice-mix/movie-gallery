package com.example.moviegallery.dao.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserGeoLocation {
    private Integer uid;

    private String location;

    private LocalDateTime updated_time;
}
