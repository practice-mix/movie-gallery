package com.example.moviegallery.controller.model;

import lombok.Data;

@Data
public class ChatPostMsg {
    private String type;//client produce and consume. ie  chat,signal
    private int uid;
    private int peerUid;

    private String content;
}
