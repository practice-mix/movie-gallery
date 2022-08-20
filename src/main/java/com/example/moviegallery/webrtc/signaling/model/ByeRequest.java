package com.example.moviegallery.webrtc.signaling.model;

import lombok.Data;

@Data
public class ByeRequest {

    private String room;
    private Integer uid;
}
