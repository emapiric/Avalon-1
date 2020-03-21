package service.impl;

import endpoint.ServerEndpoint;
import service.RoomEndpointService;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class RoomEndpointServiceImpl implements RoomEndpointService {
    @Override
    public Set<Session> getRoomSessions(String roomId) {
        return ServerEndpoint.activeRooms.get(roomId);
    }

    @Override
    public void sendToRoom(String message, Session session, String roomId) throws IOException {
        Set<Session> roomSessions = getRoomSessions(roomId);

        for (Session s: roomSessions
             ) {
            if (!s.getId().equals(session.getId())) {
                    s.getBasicRemote().sendText(message + " has entered the room.");
            }
            else{
                s.getBasicRemote().sendText("Entered room: " + roomId);
            }
        }

    }
}
