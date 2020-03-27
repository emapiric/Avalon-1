package service;

import domain.Player;
import domain.Room;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;

public interface RoomEndpointService {

    String playersInRoom(String roomId, String playerId, Set<Room> rooms, Session session);

    void newSession(String message, String roomId, String playerId, Set<Room> rooms, Session session);

    Session findPlayer(String roomId, String playerId, Set<Room> rooms);

    void sendToAll(String message, String roomId, Set<Room> rooms);

    void startGame(String roomId, Set<Room> rooms);
}
