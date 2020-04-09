package service.impl;

import Characters.CharactersName;
import domain.Command;
import domain.Room;
import service.GameEndpointService;
import sun.awt.image.ImageWatched;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class GameEndpointServiceImpl implements GameEndpointService {
    @Override
    public String Nominate(Room room, Command command) {
        return null;
    }

    @Override
    public String Vote(Room room, Command command) {
        return null;
    }

    @Override
    public String MissionVote(Room room, Command command) {
        return null;
    }

    @Override
    public String AssasinKill(Room room, Command command) {
        return null;
    }

    @Override
    public void setPlayersRoll(Room room, Command command) {

        firstSetRoll(room);

    }

    private void firstSetRoll(Room room){
        int frequency=1;
        LinkedList<Session> listOfCharacters=new LinkedList<Session>();
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            giveTheNameToPlayers(frequency,s,room.getNumberOfPlayers(),listOfCharacters);
            frequency++;


        }





    }

    private void specialCases(Room room,LinkedList<Session> listOfCharacters){



    }


    private void  giveTheNameToPlayers(int frequency,Session session,int numberOfPlayers,LinkedList<Session> listOfCharacters){

        switch(frequency){

            case 1:
                session.getUserProperties().put("roll", CharactersName.Merlin.toString());
               listOfCharacters.add(0,session);

                break;
            case 2:
                session.getUserProperties().put("roll", CharactersName.Morgana.toString());
                break;
            case 3:
                session.getUserProperties().put("roll", CharactersName.Percival.toString());
                break;
            case 4:
                session.getUserProperties().put("roll", CharactersName.Assassin.toString());
                break;
            case 5:
                session.getUserProperties().put("roll", CharactersName.Pleb1.toString());
                break;
            case 6:
                session.getUserProperties().put("roll", CharactersName.Pleb2.toString());
                break;
            case 7:
                if(numberOfPlayers==7)
                    session.getUserProperties().put("roll", CharactersName.Oberon.toString());
                else{
                    session.getUserProperties().put("roll", CharactersName.Pleb3.toString());
                }

                break;
            case 8:
                session.getUserProperties().put("roll", CharactersName.Mordred.toString());

                break;
            case 9:
                session.getUserProperties().put("roll", CharactersName.Oberon.toString());

                break;
            case 10:
                session.getUserProperties().put("roll", CharactersName.Lancelot.toString());
                break;

        }

    }
}
