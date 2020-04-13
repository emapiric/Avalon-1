package server;

import com.google.gson.Gson;
import domain.Command;
import domain.Room;
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


     public static boolean playersConnected = false;

    public void run(){

        //Cekam igrace da udju
        while(!playersConnected){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("KRECEM DALJE");


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



        while(true){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(room.IsOnMovePlayer()==true){
                System.out.println("Ukinuo sam se ");
                currentMove++;
                System.out.println("Trenutni potez je "+currentMove);
                if(currentMove==room.getNumberOfPlayers()+1){
                    System.out.println("Opet sam 1");
                    currentMove=1;
                    try {
                        gameEndpointService.sendPlayersWhoIsOnMove(room,hashMap,currentMove);
                        room.setOnMovePlayer(false);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        e.printStackTrace();
                    }

                }else{
                    try {
                        gameEndpointService.sendPlayersWhoIsOnMove(room,hashMap,currentMove);
                        room.setOnMovePlayer(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    public void sendToAll2(String message,Set<Session> players){
        Gson gson=new Gson();
        Command command=new Command(message,message);
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            s.getUserProperties().put("vote","null");

            try {
//                System.out.println(s.getUserProperties().get("username") + " saljem " + message);
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
