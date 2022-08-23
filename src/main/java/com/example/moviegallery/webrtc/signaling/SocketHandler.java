package com.example.moviegallery.webrtc.signaling;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.moviegallery.webrtc.signaling.constants.EventConstants;
import com.example.moviegallery.webrtc.signaling.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * act as signaling server for WebRTC.
 */
@Service
public class SocketHandler {

    private final Map<String, SocketIOClient> onlineUserMap = new ConcurrentHashMap<>();

    private final Map<Integer, Boolean> userBusyStatusMap = new ConcurrentHashMap<>();


    @Autowired
    private SocketIOServer server;


    @OnConnect
    public void onConnect(SocketIOClient client) {
        String uid = client.getHandshakeData().getSingleUrlParam(EventConstants.UID);
        System.out.println("onConnect,uid:" + uid);
        onlineUserMap.put(uid, client);

    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String uid = client.getHandshakeData().getSingleUrlParam(EventConstants.UID);
        System.out.println("onDisconnect,uid:" + uid);
        onlineUserMap.remove(uid);
    }


    @OnEvent(EventConstants.CALL)
    public void onCall(SocketIOClient client, AckRequest ackRequest, CallRequest message) {
        System.out.println("onCall,data:" + message);
        String room = message.getRoom();

        SocketIOClient peerClient = onlineUserMap.get(message.getPeerUid().toString());
        if (peerClient != null) {
            if (userBusyStatusMap.get(message.getPeerUid()) != null
                    && userBusyStatusMap.get(message.getPeerUid())) {
                client.sendEvent(EventConstants.BUSY);
                return;
            }

            client.joinRoom(room);
            client.sendEvent(EventConstants.CREATED);
            JoinServerPush pushObject = new JoinServerPush();
            pushObject.setRoom(message.getRoom());
            pushObject.setUid(message.getPeerUid());
            pushObject.setPeerUid(message.getUid());
            userBusyStatusMap.put(message.getUid(), true);

            peerClient.sendEvent(EventConstants.JOIN, pushObject);


        } else {
            client.sendEvent(EventConstants.OFFLINE);

        }

    }

    @OnEvent(EventConstants.JOIN)
    public void onJoin(SocketIOClient client, AckRequest ackRequest, JoinRequest message) {
        System.out.println("onJoin,data:" + message);
        String room = message.getRoom();
        Collection<SocketIOClient> clientsInRoom = server.getRoomOperations(room).getClients();
        int numClients = clientsInRoom.size();
        if (numClients > 0) {
            client.joinRoom(room);
            client.sendEvent(EventConstants.JOINED, room);
            userBusyStatusMap.put(message.getUid(), true);

            server.getRoomOperations(room).sendEvent(EventConstants.READY, room);

        } else {
            client.sendEvent(EventConstants.PEER_LEAVED);
        }
    }

    @OnEvent(EventConstants.BYE)
    public void onBye(SocketIOClient client, AckRequest ackRequest, ByeRequest request) {
        System.out.println("onBye,data = " + request);
        String room = request.getRoom();
        client.leaveRoom(room);
        userBusyStatusMap.remove(request.getUid());
        BroadcastOperations roomOperations = server.getRoomOperations(room);
        if (roomOperations.getClients().size() > 0) {
            roomOperations.sendEvent(EventConstants.BYE, room);
        }
    }


    /**
     * for signaling
     */
    @OnEvent(EventConstants.MESSAGE)
    public void onMessage(SocketIOClient client, AckRequest ackRequest, SignalingMessage message) {
        System.out.println("onMessage,data = " + message);
        server.getRoomOperations(message.getRoom()).sendEvent(EventConstants.MESSAGE, message);

    }


    /**
     * test only. see $project\src\test\resources\static\my_index.html
     */
    @OnEvent("chatevent")
    public void onChatevent(SocketIOClient client, AckRequest ackRequest, ChatObject data) {
        System.out.println("onChatevent,data = " + data);
        server.getBroadcastOperations().sendEvent("chatevent", data);
    }
}
