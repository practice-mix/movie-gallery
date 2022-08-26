package com.example.moviegallery.webrtc.signaling;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.PingListener;
import com.example.moviegallery.webrtc.signaling.constants.EventConstants;
import com.example.moviegallery.webrtc.signaling.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * act as signaling server for WebRTC.
 */
@Service
@Slf4j
public class SocketHandler {

    private final Map<String, SocketIOClient> onlineUserMap = new ConcurrentHashMap<>();


    @Autowired
    private SocketIOServer server;

    public SocketHandler(SocketIOServer server) {
        this.server = server;

        server.addPingListener(new PingListener() {
            @Override
            public void onPing(SocketIOClient client) {
                String uid = client.getHandshakeData().getSingleUrlParam(EventConstants.UID);
                log.info("onPing,uid:" + uid);
                onlineUserMap.put(uid, client);

            }
        });
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String uid = client.getHandshakeData().getSingleUrlParam(EventConstants.UID);
        log.info("onConnect,uid:" + uid);
        onlineUserMap.put(uid, client);

    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String uid = client.getHandshakeData().getSingleUrlParam(EventConstants.UID);
        log.info("onDisconnect,uid:" + uid);
        onlineUserMap.remove(uid);
    }


    @OnEvent(EventConstants.CALL)
    public void onCall(SocketIOClient client, AckRequest ackRequest, CallRequest message) {
        log.info("onCall,data:" + message);
        String room = message.getRoom();

        SocketIOClient peerClient = onlineUserMap.get(message.getPeerUid().toString());
        if (peerClient != null) {
            client.joinRoom(room);
            client.sendEvent(EventConstants.CREATED);
            log.info("send_event_created," + message);
            JoinServerPush pushObject = new JoinServerPush();
            pushObject.setRoom(message.getRoom());
            pushObject.setUid(message.getPeerUid());
            pushObject.setPeerUid(message.getUid());

            peerClient.sendEvent(EventConstants.JOIN, pushObject);
            log.info("send_event_join," + message);


        } else {
            client.sendEvent(EventConstants.OFFLINE);
            log.info("send_event_offline," + message);

        }

    }

    @OnEvent(EventConstants.JOIN)
    public void onJoin(SocketIOClient client, AckRequest ackRequest, JoinRequest message) {
        log.info("onJoin,data:" + message);
        String room = message.getRoom();
        Collection<SocketIOClient> clientsInRoom = server.getRoomOperations(room).getClients();
        int numClients = clientsInRoom.size();
        if (numClients > 0) {
            client.joinRoom(room);
            client.sendEvent(EventConstants.JOINED, room);
            log.info("send_event_joined," + message);

            server.getRoomOperations(room).sendEvent(EventConstants.READY, room);
            log.info("send_event_ready," + message);

        } else {
            client.sendEvent(EventConstants.PEER_LEAVED);
            log.info("send_event_peer_leaved," + message);
        }
    }

    @OnEvent(EventConstants.BYE)
    public void onBye(SocketIOClient client, AckRequest ackRequest, ByeRequest message) {
        log.info("onBye,data = " + message);
        String room = message.getRoom();
        client.leaveRoom(room);
        BroadcastOperations roomOperations = server.getRoomOperations(room);
        if (roomOperations.getClients().size() > 0) {
            roomOperations.sendEvent(EventConstants.BYE, room);
            log.info("send_event_bye," + message);
        }
    }


    /**
     * for signaling
     */
    @OnEvent(EventConstants.MESSAGE)
    public void onMessage(SocketIOClient client, AckRequest ackRequest, SignalingMessage message) {
        log.info("onMessage,data = " + message);
        server.getRoomOperations(message.getRoom()).sendEvent(EventConstants.MESSAGE, message);
        log.info("send_event_message," + message);

    }


    /**
     * test only. see /frontend/index.html
     */
    @OnEvent("chatevent")
    public void onChatevent(SocketIOClient client, AckRequest ackRequest, ChatObject message) {
        log.info("onChatevent,data = " + message);
        server.getBroadcastOperations().sendEvent("chatevent", message);
        log.info("send_event_chatevent," + message);
    }
}
