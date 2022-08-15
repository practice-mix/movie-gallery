package com.example.moviegallery.dao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendsListDTO extends BasePageDTO {
    private Integer uid;


}
