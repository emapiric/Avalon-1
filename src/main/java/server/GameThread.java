package server;

import com.google.gson.Gson;
import domain.Command;
import domain.Room;
import domain.Vote;
import service.GameEndpointService;
import service.impl.GameEndpointServiceImpl;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class GameThread implements Runnable{

    private Room room;

    public GameThread(Room room) {
        this.room = room;
    }
    public GameEndpointService gameEndpointService=new GameEndpointServiceImpl();
    public HashMap<Integer,String> hashMap=new HashMap<>();
    public int currentMove=1;
     public LinkedList<Boolean> votes=new LinkedList<>();
     public LinkedList<String> nameVotes=new LinkedList<>();
     public int voteNumber=0;

    public void run(){

        Thread thread=    new Thread(new Runnable() {
                @Override
                public void run() {
                    room.setOnMovePlayer(false);
                    System.out.println("Current move is"+currentMove);
                    while(true){

                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(room.IsOnMovePlayer()==true){
                            System.out.println("EO ME");
                            if(currentMove==room.getPlayers().size()){
                                currentMove=1;
                            }
                            else{

                                currentMove++;
                                System.out.println("Current move is now "+currentMove);
                                try {
                                    gameEndpointService.sendPlayersWhoIsOnMove(room,hashMap,currentMove);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (EncodeException e) {
                                    e.printStackTrace();
                                }
                            }

                            room.setOnMovePlayer(false);
                        }

                    }
                }
            });
     //   thread.start();




        /*try {

            Thread.sleep(25000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        sendToAll2("Thread koristi novi session",room.getPlayers());

       try {
            gameEndpointService.setPlayersRoll(room);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    gameEndpointService.setOnMove(room,hashMap);
        try {
            gameEndpointService.sendPlayersWhoIsOnMove(room,hashMap,currentMove);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }





        while(room.IsOnMovePlayer()==false){

        }

        System.out.println("ISPOD OVOG SRANJA SAM ! ");



    }

    public void sendToAll2(String message,Set<Session> players){
        Gson gson=new Gson();
        Command command=new Command(message,message);
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            s.getUserProperties().put("vote","null");

            try {
                System.out.println(s.getUserProperties().get("username") + " saljem " + message);
                s.getBasicRemote().sendObject(gson.toJson(command));
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }

        }
    }


    public void sendToAll(String message,Room room) {



        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();

                try {
                    System.out.println(s.getUserProperties().get("username") + " saljem " + message);
                    s.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }

}
