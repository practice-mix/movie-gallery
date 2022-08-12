package com.example.moviegallery.dao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchNearbyDTO extends BasePageDTO{
    private Float latitude;
    private Float longitude;
}
