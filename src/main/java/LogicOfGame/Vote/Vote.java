package LogicOfGame.Vote;

import domain.Command;
import domain.Room;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class  Vote {

    private LinkedList<String> voteNames;
    private LinkedList<Boolean> votes;
    private String[]nominated;
    private int  totalNumberOfPlayers;
    private int voteNumber;
    private Room room;


    public Vote(Room room,String...nominated){
        voteNames=new LinkedList<>();
        votes=new LinkedList<>();
        this.nominated=nominated;
        voteNumber=1;
        this.room=room;

    }


    public void sendVotes(Command command){



        for (Iterator<Session> it = getRoom().getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();

            try {

                s.getBasicRemote().sendObject(command);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }

        }

    }





    public abstract int setVotesNumberAndOthers();

    public String[] getNominated() {
        return nominated;
    }

    public Room getRoom() {
        return room;
    }

    public void setNominated(String[] nominated) {
        this.nominated = nominated;
    }

    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    public void setTotalNumberOfPlayers(int totalNumberOfPlayers) {
        this.totalNumberOfPlayers = totalNumberOfPlayers;
    }

    public int getVoteNumber() {
        return voteNumber;
    }

    public void setVoteNumber(int voteNumber) {
        this.voteNumber = voteNumber;
    }



    public LinkedList<String> getVoteNames() {
        return voteNames;
    }

    public void setVoteNames(String name) {
        voteNames.add(name);
    }


    public LinkedList<Boolean> getVotes() {
        return votes;
    }

    public void setVotes(boolean vote) {
        votes.add(vote);

    }
}
