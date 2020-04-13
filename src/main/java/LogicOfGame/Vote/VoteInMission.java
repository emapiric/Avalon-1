package LogicOfGame.Vote;

import domain.Command;
import domain.Room;

public class VoteInMission extends Vote {

    private int quest;
    private int playerInMission;
    private int good;
    private int evil;


    public VoteInMission(Room room, String... nominated) {
        super(room, nominated);
        quest=1;
    }
    public void setPlayerInMission(int playerInMission){
        this.playerInMission=playerInMission;
    }
    @Override
    public int setVotesNumberAndOthers(){

        if(playerInMission==getVoteNumber()){

                if(missionPassedOrFailed()){
                    good++;
                    Command command=new Command("missionFinished","passed",getNominated(),numberOfNegativVotes());
                        sendVotes(command);
                }
                else{
                    evil++;
                    Command command=new Command("missionFinished","failed",getNominated(),numberOfNegativVotes());
                        sendVotes(command);
                }
            getVotes().remove(getVotes());
            getVoteNames().remove(getVoteNames());
            setVoteNumber(1);
                quest++;
                return 0;
        }
        else{

            setVoteNumber(getVoteNumber()+1);
            System.out.println("Vote iss "+getVoteNumber());
            return -1;
        }

    }
    public int getQuest() {
        return quest;
    }

    public void setQuest(int quest) {
        this.quest = quest;
    }

    public int getGood() {
        return good;
    }


    public int getEvil() {
        return evil;
    }


    private boolean missionPassedOrFailed(){

        int FALSE=0;

        if(specialCases()){
            for (boolean b:getVotes()) {
                if(FALSE==2)
                    return false;

                if(b==false)
                    FALSE++;


            }

        }
        //Dovoljno  je jedan glas
        else{
            for (boolean b:getVotes()) {
                if(b==false)
                    return false;
            }
        }
        return true;

    }
    public boolean specialCases(){

        return (getRoom().getNumberOfPlayers()>=7 && quest==4)?true:false;
    }
    private int numberOfNegativVotes(){
        int numberOfNegativeVotes=0;

        for (boolean b:getVotes()) {

            if(b==false)
                numberOfNegativeVotes++;
        }
        return numberOfNegativeVotes;

    }
}
