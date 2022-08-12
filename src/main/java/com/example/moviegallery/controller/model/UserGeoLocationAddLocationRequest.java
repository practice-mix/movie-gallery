package com.example.moviegallery.controller.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserGeoLocationAddLocationRequest {

    @NotNull
    private Integer uid;
    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;

}
