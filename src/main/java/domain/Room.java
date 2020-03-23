package domain;

import javax.websocket.Session;
import java.util.*;

public class Room{

    private boolean active;
    private String roomId;
    private Set<Session> players;
    private Set<String> outOfGame;
//    private List<domain.Player> players;


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

//    public Room() {
//        this.players = new LinkedList<domain.Player>();
//        this.roomId = createRoomId();
//    }


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

//    public List<domain.Player> getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(List<domain.Player> players) {
//        this.players = players;
//    }

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

//    private static int createRoomId() {
//        int n = 4;
//        String NumericString = "0123456789";
//
//        StringBuilder sb = new StringBuilder(n);
//
//        for (int i = 0; i < n; i++) {
//            int index
//                    = (int)(NumericString.length()
//                    * Math.random());
//            sb.append(NumericString
//                    .charAt(index));
//        }
//        return Integer.parseInt(sb.toString());
//    }


}
