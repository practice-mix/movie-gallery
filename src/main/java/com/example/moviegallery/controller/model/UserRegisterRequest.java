package com.example.moviegallery.controller.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRegisterRequest {


    @NotEmpty
    private String nickname;

    private String avatar;

    @NotEmpty
    private String phone_number;
}
