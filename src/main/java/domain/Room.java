package domain;

import LogicOfGame.Vote.VoteForMission;
import LogicOfGame.Vote.VoteInMission;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class Room{

    private boolean active;
    private String roomId;
    private Set<Session> players;
    private Set<String> outOfGame;
    private int nominationNumber;
    private int quest;
    private int numberOfPlayers;
    private boolean onMovePlayer;
    private int voteNumber=1;
    private LinkedList<String> voteNames=new LinkedList<String>();
    private LinkedList<Boolean> votes=new LinkedList<Boolean>();
    private String[]nominated;

    //Predstavlja nominovane igrace!

    public VoteForMission voteForMission=new VoteForMission(this,nominated);
    public VoteInMission voteInMission=new VoteInMission(this,nominated);

    public void setNominated(String...nominated){
        this.nominated=nominated;
    }

    public Room(String roomId) {
        this.active = false;
        this.roomId = roomId;
        this.players = Collections.synchronizedSet(new HashSet<Session>());
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public boolean IsOnMovePlayer() {
        return onMovePlayer;
    }

    public void setOnMovePlayer(boolean onMovePlayer) {
        this.onMovePlayer = onMovePlayer;
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

    public void sendVotes(Command command){

        for (Iterator<Session> it = getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();

            try {

                s.getBasicRemote().sendObject(command);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", players=" + players +
                '}';
    }
}
