package com.example.moviegallery.dao.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGeoLocationCoordinate {
    /**
     * Column: uid
     * Type: INT
     */
    private Integer uid;

    private Float latitude;
    private Float longitude;

    /**
     * Column: updated_time
     * Type: DATETIME
     * Default value: CURRENT_TIMESTAMP
     */
    private LocalDateTime updatedTime;


}
