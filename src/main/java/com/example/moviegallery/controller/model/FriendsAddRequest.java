package com.example.moviegallery.controller.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FriendsAddRequest {

    @NotNull
    private Integer uid;

    @NotNull
    private Integer friend_uid;
}
