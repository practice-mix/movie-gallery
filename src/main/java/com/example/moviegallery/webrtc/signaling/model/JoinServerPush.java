package com.example.moviegallery.webrtc.signaling.model;

import lombok.Data;

@Data
public class JoinServerPush {

    private String room;
    private Integer uid;
    private Integer peerUid;

}
