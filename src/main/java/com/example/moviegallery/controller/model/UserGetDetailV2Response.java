package com.example.moviegallery.controller.model;

import lombok.Data;

@Data
public class UserGetDetailV2Response {
//========= User fields
    private Integer uid;

    private String nickname;

    private String avatar;

    private String phone_number;

//========== friend fields
    private Boolean areFriend;

}
