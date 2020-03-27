package endpoint;

import domain.Room;
import domain.decoder.CommandDecoder;
import domain.decoder.PlayerDecoder;
import domain.encoder.CommandEncoder;
import domain.encoder.PlayerEncoder;
import service.GameEndpointService;
import service.RoomEndpointService;
import service.impl.GameEndpointServiceImpl;
import service.impl.RoomEndpointServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint(value = "/Server/Game/{roomId}/{playerId}", decoders = CommandDecoder.class, encoders = CommandEncoder.class)
public class GameEndpoint {


    private Logger logger = Logger.getLogger(this.getClass().getName());
    public GameEndpointService gameEndpointService = new GameEndpointServiceImpl();
    public static Set<Room> rooms = Collections.synchronizedSet(new HashSet<Room>());

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) throws IOException {
        logger.info("Connected in room: " + roomId);
        session.getBasicRemote().sendText("Connected in room: " + roomId);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) {
        System.out.println("Username " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

}
