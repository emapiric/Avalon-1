package domain;



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

    public void setNominated(String...nominated){
        this.nominated=nominated;
    }
    public int getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber() {
        if(voteNumber==numberOfPlayers){
            System.out.println("Vote iss "+voteNumber);
            Boolean []votes1=new Boolean[votes.size()];
            votes.toArray(votes1);
            String[] nameVotes1=new String[voteNames.size()];
            voteNames.toArray(nameVotes1);

            Command command=new Command("nominatedVote",nameVotes1,votes1);

            sendVotes(command);
            votes.remove();
            voteNames.remove();
            this.voteNumber=1;
        }
        else{
            System.out.println("Vote is "+voteNumber);
            this.voteNumber++;
        }

    }

    public void setVote(boolean vote){
        votes.addFirst(vote);
        setVoteNumber();
    }
    public void setNameVote(String name){
        voteNames.addFirst(name);

    }



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
