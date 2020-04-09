package server;

import com.google.gson.Gson;
import domain.Command;
import domain.Room;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class GameThread extends Thread{

    private Room room;

    public GameThread(Room room) {
        this.room = room;
    }

    public void run(){


        System.out.println("U game threadu sam");

        sendToAll("Usli ste u gameThread",room);

        /*try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        sendToAll2("Thread koristi novi session",room);


        while(true){

        }





        /**
         *
         * LOGIKA IGRICE
         *
         * Poslati svima koji su likovi
         * Prvi igrac daje predlog. server dobija command objekat sa listom ljudi koje je on predlozio
         * Ako je 4. predlog onda se bira izmedju 4 i 5 igraca
         *
         * Vise od pola dalo DA onda se glasa za misiju =>
         *
         */
    }

    public void sendToAll2(String message,Room room){
        Gson gson=new Gson();
        Command command=new Command(message,message);
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();

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
