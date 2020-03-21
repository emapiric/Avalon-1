package domain;

public class Player {

    private String username;
    private String playerId;
    private String roomId;

    public Player(String username, String playerId, String roomId) {
        this.username = username;
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", playerId='" + playerId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }

}
