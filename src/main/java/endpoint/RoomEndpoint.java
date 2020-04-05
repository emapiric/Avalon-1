package endpoint;

import domain.decoder.PlayerDecoder;
import domain.encoder.PlayerEncoder;
import domain.Room;
import service.RoomEndpointService;
import service.ServerEndpointService;
import service.impl.RoomEndpointServiceImpl;
import service.impl.ServerEndpointServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@javax.websocket.server.ServerEndpoint(value = "/Server/{roomId}/{playerId}", decoders = PlayerDecoder.class, encoders = PlayerEncoder.class)
public class RoomEndpoint {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    public RoomEndpointService roomEndpointService = new RoomEndpointServiceImpl();
    boolean firstPlayer;
    int numberOfPlayers=0;

    public static Set<Room> rooms = ServerEndpoint.rooms;

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) throws IOException {
        logger.info("Connected in room: " + roomId);
        if(firstPlayer==false){
            rooms.add(new Room("1"));
            firstPlayer=true;
        }
        session.getUserProperties().put("roomId",roomId);
        session.getUserProperties().put("playerId",playerId);
        Room room=new Room("i");
        room.addPlayer(session);
        session.getBasicRemote().sendText("You entered in a room");
        numberOfPlayers++;
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) {

        switch (message){
            case "startGame":
                roomEndpointService.startGame(roomId, rooms);
                break;

            default:

                roomEndpointService.newSession(message, roomId,playerId,rooms,session);
                String playerInRoom = roomEndpointService.playersInRoom(roomId,playerId,rooms,session);
                System.out.println("Players in room " + roomId +": " + playerInRoom);
                roomEndpointService.sendToAll(playerInRoom,roomId,rooms);
                if(numberOfPlayers==5){
                    roomEndpointService.startGame(roomId,rooms);
                }
                break;

        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

}
