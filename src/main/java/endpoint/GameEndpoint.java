package endpoint;

import domain.Command;
import domain.Room;
import domain.decoder.CommandDecoder;
import domain.decoder.PlayerDecoder;
import domain.encoder.CommandEncoder;
import domain.encoder.PlayerEncoder;
import service.GameEndpointService;
import service.RoomEndpointService;
import service.ServerEndpointService;
import service.impl.GameEndpointServiceImpl;
import service.impl.RoomEndpointServiceImpl;
import service.impl.ServerEndpointServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint(value = "/Server/{roomId}/{playerId}/Game", decoders = CommandDecoder.class, encoders = CommandEncoder.class)
public class GameEndpoint {


    private Logger logger = Logger.getLogger(this.getClass().getName());
    public GameEndpointService gameEndpointService = new GameEndpointServiceImpl();
    public ServerEndpointService serverEndpointService= new ServerEndpointServiceImpl();
    public static Set<Room> rooms = endpoint.ServerEndpoint.rooms;

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) throws IOException {
        logger.info("Connected in room: " + roomId);
        newSession(roomId,playerId,session);



    }

    @OnMessage
    public void onMessage(Command command, Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) {

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }


    public void newSession(String roomId, String playerId,Session session) {
        Room room = serverEndpointService.findRoom(roomId,rooms);
        if(room == null)
            System.out.println("mrtvi room null");
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            System.out.println(s.getUserProperties().get("playerId") + " igrac");
            if (s.getUserProperties().get("playerId").equals(playerId)){
                System.out.println("Menjam session");
                String username=s.getUserProperties().get("username").toString();

                session.getUserProperties().put("username",username);
                session.getUserProperties().put("roomId",roomId);
                session.getUserProperties().put("playerId",playerId);
                room.getPlayers().remove(s);
                room.getPlayers().add(session);

                return;
            }
        }
    }

}
