package com.example.moviegallery.controller.model;

import lombok.Data;

@Data
public class ChatPostMsg {
    private int uid;
    private int peerUid;

    private String content;
}
