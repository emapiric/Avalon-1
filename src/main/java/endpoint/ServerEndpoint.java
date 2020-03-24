package endpoint;

import domain.decoder.PlayerDecoder;
import domain.encoder.PlayerEncoder;
import domain.Room;
import service.ServerEndpointService;
import service.impl.ServerEndpointServiceImpl;

import javax.websocket.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

//izbaci usera iz seta kad zatvori sessio
@javax.websocket.server.ServerEndpoint(value = "/Server", decoders = PlayerDecoder.class, encoders = PlayerEncoder.class)
public class ServerEndpoint{


    private Logger logger = Logger.getLogger(this.getClass().getName());
    private ServerEndpointService serverEndpointService = new ServerEndpointServiceImpl();

    public static Set<Session> players = Collections.synchronizedSet(new HashSet<Session>());
//    public static Map<String,Set<Session>> activeRooms = new ConcurrentHashMap<>();

    public static Set<Room> rooms = Collections.synchronizedSet(new HashSet<Room>());

    @OnOpen
    public void onOpen(Session session) throws IOException {
        logger.info("New player: " + session.getId());
        players.add(session);
        session.getBasicRemote().sendText("Connected!");
    }

    @OnMessage
    public void onMessage(domain.Player player, Session session) throws IOException {
        System.out.println("I got " + player);

        switch (player.getCommand()){
            case "newPlayer":
                player = serverEndpointService.newPlayer(player,rooms,session);
                break;
            case "enterRoom":
                player = serverEndpointService.enterRoom(player,rooms,session);
                break;
            case "reconnect":
                player = serverEndpointService.reconnect(player,rooms,session);
                break;
            case "updateUsername":
                player = serverEndpointService.updateUsername(player,rooms,session);
                break;
            default:
                session.getBasicRemote().sendText("Wrong command!");
                return;
        }

        try {
            System.out.println("I return " + player);
            System.out.println("Iz session properites a: " + session.getUserProperties().get("username"));
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
