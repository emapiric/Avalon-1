package service.impl;

import domain.Player;
import domain.Room;
import service.ServerEndpointService;

import javax.websocket.Session;
import java.util.*;

public class ServerEndpointServiceImpl implements ServerEndpointService {

    //Slucaj kad dodeli dvojici playera isti ID - treba dodati
    @Override
    public Player newPlayer(Player player, Set<Room> rooms, Session session) {
        player.setPlayerId(createId(4));
        String roomId = createId(4);
        boolean roomIdIsOK = false;

        while(!roomIdIsOK) {
            if (!checkRoom(roomId, rooms)) {
                roomIdIsOK = true;
            }
        }
        player.setRoomId(roomId);

        rooms.add(new Room(player.getRoomId()));
        addPlayer(player,rooms,session);
        return  player;
    }

    @Override
    public Player enterRoom(Player player, Set<Room> rooms, Session session) {
        if(!checkRoom(player.getRoomId(),rooms)){
            player.setRoomId("Room does not exist");
            return player;
        }
        if(isFullRoom(player.getRoomId(),rooms)){
            player.setRoomId("Room is full");
            return player;
        }
        else {
            player.setPlayerId(createId(4));
            addPlayer(player,rooms,session);
        }

        return player;

    }

    @Override
    public Player reconnect(Player player, Set<Room> rooms, Session session) {
        if(!canReconnect(player.getRoomId(),player.getPlayerId(),rooms)){
            player.setRoomId("Failed to reconnect");
        }
        else{
            addPlayer(player,rooms,session);
        }

        return player;
    }

    @Override
    public boolean isFullRoom(String roomId, Set<Room> rooms) {
        return findRoom(roomId,rooms).getPlayers().size() >= 5;
    }

    @Override
    public boolean checkRoom(String roomId, Set<Room> rooms) {
        if(rooms == null) return false;
        return findRoom(roomId,rooms) != null;
    }

    @Override
    public boolean isActiveRoom(String roomId, Set<Room> rooms) {
        return findRoom(roomId,rooms).isActive();
    }

    @Override
    public boolean canReconnect(String roomId, String playerId, Set<Room> rooms) {
        Room room = findRoom(roomId,rooms);
        for (String player: room.getOutOfGame()
             ) {
            if(playerId.equals(player))
                return true;
        }
        return  false;
    }

    @Override
    public Room findRoom(String roomId, Set<Room> rooms) {
        for (Iterator<Room> it = rooms.iterator(); it.hasNext(); ) {
            Room r = it.next();

            if (r.getRoomId().equals(roomId)){
                return r;
            }

        }
        return null;
    }

    @Override
    public void addPlayer(Player player, Set<Room> rooms, Session session) {
        Room room = findRoom(player.getRoomId(),rooms);
        room.addPlayer(session);
        session.getUserProperties().put("username",player.getUsername());
        session.getUserProperties().put("playerId",player.getPlayerId());
        session.getUserProperties().put("roomId",player.getRoomId());
    }

    private static String createId(int n) {
        String NumericString = "0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int)(NumericString.length()
                    * Math.random());
            sb.append(NumericString
                    .charAt(index));
        }
        return sb.toString();
    }
}
