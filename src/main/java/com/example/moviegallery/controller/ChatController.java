package com.example.moviegallery.controller;

import com.example.moviegallery.controller.model.ChatPostMsg;
import com.example.moviegallery.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class ChatController {
    @Autowired
    private MqttClient mqttClient;

    @PostMapping("/chat/postMsg")
    public ResponseEntity<Void> postMsg(@RequestBody ChatPostMsg request) throws MqttException, JsonProcessingException {
        String msg = JsonUtil.getObjectMapper().writer().writeValueAsString(request);
        mqttClient.publish("u/" + request.getPeerUid(),
                msg.getBytes(StandardCharsets.UTF_8),
                2, false
        );


        return ResponseEntity.ok(null);
    }

}
