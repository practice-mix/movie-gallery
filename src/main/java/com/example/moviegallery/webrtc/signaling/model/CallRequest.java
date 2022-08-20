package com.example.moviegallery.webrtc.signaling.model;

import lombok.Data;

@Data
public class CallRequest {
    private String room;
    private Integer uid;
    private Integer peerUid;


}
