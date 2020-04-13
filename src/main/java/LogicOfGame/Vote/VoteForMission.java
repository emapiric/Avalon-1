package LogicOfGame.Vote;

import domain.Command;
import domain.Room;
import service.ServerEndpointService;
import service.impl.ServerEndpointServiceImpl;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;

public class VoteForMission extends Vote {
    public VoteForMission( Room room, String... nominated) {
        super(room, nominated);
    }


    @Override
    public int setVotesNumberAndOthers() {

        if(getRoom().getNumberOfPlayers()==getVoteNumber()){


            System.out.println("Vote iss "+getVoteNumber());
            Boolean []votes1=new Boolean[getVotes().size()];
            getVotes().toArray(votes1);
            String[] nameVotes1=new String[getVoteNames().size()];
            getVoteNames().toArray(nameVotes1);

            Command command=new Command("nominatedVote",nameVotes1,votes1);

            sendVotes(command);
            if(goOnMissionOrNot(votes1)){

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Command command1=new Command("missionStarted",null,getNominated());
                getVoteNames().remove();
                getVotes().remove();
                setVoteNumber(1);
                sendVotes(command);
                //Ako je misija pocela,vraca 0
                return 0;
            }
            else{
                getVoteNames().remove();
                getVotes().remove();
                setVoteNumber(1);
                //Ako nije pocela vraca 1
            return 1;

            }


        }
        else{
            setVoteNumber(getVoteNumber()+1);
            System.out.println("Vote iss "+getVoteNumber());
            return -1;
        }
    }



    public boolean goOnMissionOrNot(Boolean[] votes){
        int TRUE=0;
        int FALSE=0;
        for (boolean b:votes) {
            if(b==true)
                TRUE++;
            else
                FALSE++;
        }

        if(TRUE>FALSE)
            return true;
        else
            return false;

    }
}
