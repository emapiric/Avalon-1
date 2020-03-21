package endpoint;

import domain.PlayerDecoder;
import domain.PlayerEncoder;
import domain.Room;
import service.ServerEndpointService;
import service.impl.ServerEndpointServiceImpl;

import javax.json.*;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

//izbaci usera iz seta kad zatvori sessio
@javax.websocket.server.ServerEndpoint(value = "/Server", decoders = PlayerDecoder.class, encoders = PlayerEncoder.class)
public class ServerEndpoint{


    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ServerEndpointService serverEndpointService = new ServerEndpointServiceImpl();

    public static Set<Session> players = Collections.synchronizedSet(new HashSet<Session>());
    public static Map<String,Set<Session>> activeRooms = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        players.add(session);
        session.getBasicRemote().sendText("Connected");
    }

    @OnMessage
    public void onMessage(domain.Player player, Session session) throws IOException {
        System.out.println("I got " + player);
        String messageForClient = serverEndpointService.allocate(player,activeRooms,session);
        System.out.println(messageForClient);
        if(!messageForClient.equals("OK")){
            session.getBasicRemote().sendText(messageForClient);
            return;
        }

        session.getUserProperties().put("username",player.getUsername());
        session.getUserProperties().put("playerId", player.getPlayerId());
        session.getUserProperties().put("roomId",player.getRoomId());

        logger.info("New user: " + player.getUsername());
        try {
            System.out.println("I return " + player);
            session.getBasicRemote().sendObject(player);
        } catch (EncodeException e) {
            e.printStackTrace();
        }

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }


}
