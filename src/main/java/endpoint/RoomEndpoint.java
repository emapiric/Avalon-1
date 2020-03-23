package endpoint;

import domain.PlayerDecoder;
import domain.PlayerEncoder;
import domain.Room;
import service.RoomEndpointService;
import service.impl.RoomEndpointServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@javax.websocket.server.ServerEndpoint(value = "/Server/{roomId}", decoders = PlayerDecoder.class, encoders = PlayerEncoder.class)
public class RoomEndpoint {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    public RoomEndpointService roomEndpointService = new RoomEndpointServiceImpl();
//    private static Map<String, Set<Session>> activeRooms = new ConcurrentHashMap<>();

    public static Set<Room> rooms = Collections.synchronizedSet(new HashSet<Room>());

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) throws IOException {
        logger.info("Connected in room: " + roomId);
        session.getBasicRemote().sendText("Connected in room: " + roomId);
    }


    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomId") String roomId) {
        System.out.println("Username " + message);
//        for(Session s: roomEndpointService.getRoomSessions(roomId)){
//            System.out.println(s.getId());
//        }
//        try {
//            roomEndpointService.sendToRoom(message,session,roomId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));

    }

}
