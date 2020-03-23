package service;

import domain.Player;
import domain.Room;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;

public interface ServerEndpointService {

    public Player newPlayer(Player player, Set<Room> rooms, Session session);

    public Player enterRoom(Player player, Set<Room> rooms, Session session);

    public Player reconnect(Player player, Set<Room> rooms, Session session);

    public boolean isFullRoom(String roomId, Set<Room> rooms);

    //Does it exist
    //true = room exists
    //false = room does not exist
    public boolean checkRoom(String roomId, Set<Room> rooms);

    //Is it active room
    public boolean isActiveRoom(String roomId, Set<Room> rooms);

    //Can player reconnect
    public boolean canReconnect(String roomId, String playerId, Set<Room> rooms);

    public Room findRoom(String roomId, Set<Room> rooms);

    public void addPlayer(Player player, Set<Room> rooms, Session session);

}
