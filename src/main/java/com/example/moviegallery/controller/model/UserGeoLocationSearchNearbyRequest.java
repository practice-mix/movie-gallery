package com.example.moviegallery.controller.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserGeoLocationSearchNearbyRequest extends BasePageRequest{

    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;


}
