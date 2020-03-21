package domain;

import java.util.LinkedList;
import java.util.List;

public class Room{

    private int roomId;
    private List<domain.Player> players;

    public Room(int roomId) {
        this.roomId = roomId;
        this.players = new LinkedList<>();
    }

    public Room() {
        this.players = new LinkedList<domain.Player>();
        this.roomId = createRoomId();
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<domain.Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<domain.Player> players) {
        this.players = players;
    }

    public void addPlayer(domain.Player player){
        players.add(player);
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", players=" + players +
                '}';
    }

    private static int createRoomId() {
        int n = 4;
        String NumericString = "0123456789";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index
                    = (int)(NumericString.length()
                    * Math.random());
            sb.append(NumericString
                    .charAt(index));
        }
        return Integer.parseInt(sb.toString());
    }


}
