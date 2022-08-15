package com.example.moviegallery.controller.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class FriendsListRequest extends BasePageRequest{

    @NotNull
    private Integer uid;



}
