package com.example.moviegallery.webrtc.signaling.model;

import lombok.Data;

@Data
public class SignalingMessage {
    private String type;
    private String room;
    private Object content;//json, transparent
}
