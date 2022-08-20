package com.example.moviegallery.webrtc.signaling.model;

import lombok.Data;

@Data
public class JoinRequest {
    private Integer uid;

    private String room;

}
