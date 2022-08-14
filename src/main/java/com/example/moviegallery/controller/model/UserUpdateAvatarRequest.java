package com.example.moviegallery.controller.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserUpdateAvatarRequest {

    @NotEmpty
    private Integer uid;
    @NotEmpty
    private String avatar;
}
