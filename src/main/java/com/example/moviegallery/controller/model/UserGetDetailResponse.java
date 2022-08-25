package com.example.moviegallery.controller.model;

import lombok.Data;

@Data
public class UserGetDetailResponse {
    //========= User fields
    private Integer uid;

    private String nickname;

    private String avatar;

    private String phone_number;


}
