package service.impl;

import domain.Player;
import service.ServerEndpointService;

import javax.websocket.Session;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServerEndpointServiceImpl implements ServerEndpointService {

    //dorati slucaj kad dodeli isti id dvojici playera ili room
    @Override
    public String allocate(Player player, Map<String, Set<Session>> activeRooms, Session session) {

        if(player == null) {
            return "Error sending player";
        }
        if(player.getUsername() == null) {
            return "Wrong username";
        }

        //new player
        if(player.getPlayerId() == null && player.getRoomId() == null){
            newPlayer(player,activeRooms,session);
        }

        if(player.getPlayerId() == null && player.getRoomId() != null){
            boolean roomExists = false;
            for (String s: activeRooms.keySet()
                 ) {
                if(s.equals(player.getRoomId()))
                    roomExists = true;
            }

            if(!roomExists)
                newPlayer(player,activeRooms,session);
            else
                player.setPlayerId(createId(4));

        }

        return "OK";
    }

    @Override
    public void newPlayer(Player player, Map<String, Set<Session>> activeRooms, Session session) {
        player.setPlayerId(createId(4));
        player.setRoomId(createId(4));
        Set<Session> newRoom = Collections.synchronizedSet(new HashSet<Session>());
        newRoom.add(session);
        activeRooms.put(player.getRoomId(), newRoom);
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
