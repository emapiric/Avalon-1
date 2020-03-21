package service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

public interface RoomEndpointService {

    public Set<Session> getRoomSessions(String roomId);

    public void sendToRoom(String message, Session session, String roomId) throws IOException;

}
