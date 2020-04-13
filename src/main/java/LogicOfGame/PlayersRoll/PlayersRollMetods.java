package LogicOfGame.PlayersRoll;


import domain.Command;
import domain.Room;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class PlayersRollMetods {




    public void setPlayersRoll(Room room) throws IOException, EncodeException {
        int frequency=1;
        LinkedList<Session> listOfCharacters=new LinkedList<Session>();
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            giveANameToPlayers(frequency,s,room.getNumberOfPlayers(),listOfCharacters);
            frequency++;


        }

        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            sendToServerRolls(s,listOfCharacters);


        }
    }



    private void sendToServerRolls(Session session,LinkedList<Session> listOfCharacters) throws IOException, EncodeException {
        if(session.getUserProperties().get("roll").equals("Merlin")){
            Command command=null;
            if(listOfCharacters.size()==5 || listOfCharacters.size()==6){
                String Morgana=session.getUserProperties().get("Morgana").toString();
                String Assassin=session.getUserProperties().get("Assassin").toString();
                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Morgana,Assassin);
            }
            if(listOfCharacters.size()==7 || listOfCharacters.size()==10){
                String Morgana=session.getUserProperties().get("Morgana").toString();
                String Assassin=session.getUserProperties().get("Assassin").toString();
                String Oberon=session.getUserProperties().get("Oberon").toString();
                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Morgana,Assassin,Oberon);
            }

            if(listOfCharacters.size()==8){
                String Morgana=session.getUserProperties().get("Morgana").toString();
                String Assassin=session.getUserProperties().get("Assassin").toString();
                String Mordred=session.getUserProperties().get("Mordred").toString();

                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Morgana,Assassin,Mordred);
            }

            if(listOfCharacters.size()==9){
                String Morgana=session.getUserProperties().get("Morgana").toString();
                String Assassin=session.getUserProperties().get("Assassin").toString();
                String Mordred=session.getUserProperties().get("Mordred").toString();
                String Oberon=session.getUserProperties().get("Oberon").toString();

                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Morgana,Assassin,Mordred,Oberon);
            }


            session.getBasicRemote().sendObject(command);
        }

        if(session.getUserProperties().get("roll").equals("Morgana")){
            Command command=null;
            if(listOfCharacters.size()>=5 && listOfCharacters.size()<=7) {

                String Assassin=session.getUserProperties().get("Assassin").toString();
                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Assassin);
            }

            if(listOfCharacters.size()>=8 && listOfCharacters.size()<=10){

                String Assassin=session.getUserProperties().get("Assassin").toString();
                String Mordred=session.getUserProperties().get("Mordred").toString();

                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Assassin,Mordred);
            }
            session.getBasicRemote().sendObject(command);
        }

        if(session.getUserProperties().get("roll").equals("Percival")){
            Command command;

            String Merlin=session.getUserProperties().get("Merlin").toString();
            String Morgana=session.getUserProperties().get("Morgana").toString();
            String Roll=session.getUserProperties().get("roll").toString();
            if(randomMorganaMerlin()==0)
                command=new Command("roll",Roll,Merlin,Morgana);
            else{
                command=new Command("roll",Roll,Morgana,Merlin);
            }
            session.getBasicRemote().sendObject(command);
        }

        if(session.getUserProperties().get("roll").equals("Assassin")){
            Command command=null;
            if(listOfCharacters.size()>=5 && listOfCharacters.size()<=7) {

                String Morgana=session.getUserProperties().get("Morgana").toString();
                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Morgana);
            }

            if(listOfCharacters.size()>=8 && listOfCharacters.size()<=10){

                String Morgana=session.getUserProperties().get("Morgana").toString();
                String Mordred=session.getUserProperties().get("Mordred").toString();

                String Roll=session.getUserProperties().get("roll").toString();
                command=new Command("roll",Roll,Morgana,Mordred);
            }
            session.getBasicRemote().sendObject(command);
        }

        if(session.getUserProperties().get("roll").equals("Mordred")){
            Command command=null;

            String Morgana=session.getUserProperties().get("Morgana").toString();
            String Assassin=session.getUserProperties().get("Assassin").toString();

            String Roll=session.getUserProperties().get("roll").toString();
            command=new Command("roll",Roll,Morgana,Assassin);
            session.getBasicRemote().sendObject(command);
        }

        //Ostali su Pleb1,Pleb2,Pleb3,Oberon i onaj na Lancetron kako se vec zove...
        else{
            String Roll=session.getUserProperties().get("roll").toString();
            Command command=new Command("roll",Roll);
            session.getBasicRemote().sendObject(command);
        }






    }

    private void  giveANameToPlayers(int frequency, Session session, int numberOfPlayers, LinkedList<Session> listOfCharacters){

        switch(frequency){

            case 1:
                session.getUserProperties().put("roll", CharactersName.Merlin.toString());
                listOfCharacters.add(0,session);


                break;
            case 2:
                session.getUserProperties().put("roll", CharactersName.Morgana.toString());
                listOfCharacters.add(1,session);
                //Ova metoda funkcionise tako sto prima prvi parametar koji predstavlja index onome kome zelimo da nalepimo username igraca kojeg njegov lik zna
                // u ovom slucaju zelim da nalepim Merlinu koji je uvijek index 0 . U Session.getUserProperty().put sam stavio kljuc ime igraca
                //treci parametar prosledjujem session igraca kome zelimo da uzmemo username i da prilepimo nasem igracu
                addSpecialPlayer(0,"Morgana",listOfCharacters.get(1),listOfCharacters);
                break;
            case 3:
                session.getUserProperties().put("roll", CharactersName.Percival.toString());
                listOfCharacters.add(2,session);
                addSpecialPlayer(2,"Morgana",listOfCharacters.get(1),listOfCharacters);
                addSpecialPlayer(2,"Merlin",listOfCharacters.get(0),listOfCharacters);
                break;
            case 4:
                session.getUserProperties().put("roll", CharactersName.Assassin.toString());
                listOfCharacters.add(3,session);
                addSpecialPlayer(3,"Morgana",listOfCharacters.get(1),listOfCharacters);
                addSpecialPlayer(1,"Assassin",listOfCharacters.get(3),listOfCharacters);
                addSpecialPlayer(0,"Assassin",listOfCharacters.get(3),listOfCharacters);
                break;
            case 5:
                session.getUserProperties().put("roll", CharactersName.Pleb1.toString());
                listOfCharacters.add(4,session);
                break;
            case 6:
                session.getUserProperties().put("roll", CharactersName.Pleb2.toString());
                listOfCharacters.add(5,session);
                break;
            case 7:
                if(numberOfPlayers==7){
                    session.getUserProperties().put("roll", CharactersName.Oberon.toString());
                    listOfCharacters.add(6,session);
                    addSpecialPlayer(0,"Oberon",listOfCharacters.get(6),listOfCharacters);
                }

                else{
                    session.getUserProperties().put("roll", CharactersName.Pleb3.toString());
                    listOfCharacters.add(6,session);
                }

                break;
            case 8:
                session.getUserProperties().put("roll", CharactersName.Mordred.toString());
                listOfCharacters.add(7,session);
                addSpecialPlayer(1,"Mordred",listOfCharacters.get(7),listOfCharacters);
                addSpecialPlayer(3,"Mordred",listOfCharacters.get(7),listOfCharacters);
                addSpecialPlayer(7,"Morgana",listOfCharacters.get(1),listOfCharacters);
                addSpecialPlayer(7,"Assassin",listOfCharacters.get(3),listOfCharacters);

                break;
            case 9:
                session.getUserProperties().put("roll", CharactersName.Oberon.toString());
                listOfCharacters.add(8,session);
                addSpecialPlayer(0,"Oberon",listOfCharacters.get(8),listOfCharacters);
                //Ako ima 9 igraca, znaci da Merlin zna za Mordreda
                if(numberOfPlayers==9)
                    addSpecialPlayer(0,"Mordred",listOfCharacters.get(7),listOfCharacters);
                break;
            case 10:


                session.getUserProperties().put("roll", CharactersName.Lancelot.toString());
                listOfCharacters.add(9,session);
                break;

        }

    }

    private void addSpecialPlayer(int index,String name,Session session,LinkedList<Session> listOfCharacters){
        listOfCharacters.get(index).getUserProperties().put(name,session.getUserProperties().get("username"));

    }

    private int randomMorganaMerlin(){
        Random random=new Random();
        return random.nextInt(2);
    }
}