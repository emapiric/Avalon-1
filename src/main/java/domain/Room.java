package domain;

import javax.websocket.Session;
import java.util.*;

public class Room{

    private boolean active;
    private String roomId;
    private Set<Session> players;
    private Set<String> outOfGame;
    private int nominationNumber;
    private int quest;
    private int numberOfPlayers;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Room(String roomId) {
        this.active = false;
        this.roomId = roomId;
        this.players = Collections.synchronizedSet(new HashSet<Session>());
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Set<Session> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Session> players) {
        this.players = players;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public Set<String> getOutOfGame() {
        return outOfGame;
    }

    public void setOutOfGame(Set<String> outOfGame) {
        this.outOfGame = outOfGame;
    }

    public void addPlayer(Session session){
        players.add(session);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", players=" + players +
                '}';
    }

}
