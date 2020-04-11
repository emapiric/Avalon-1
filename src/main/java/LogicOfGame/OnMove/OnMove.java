package LogicOfGame.OnMove;

import domain.Command;
import domain.Room;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class OnMove {


    public void setRandom(Room room, HashMap hashMap){

        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            setKeyToPlayer(room,hashMap,s.getUserProperties().get("username").toString());

        }

    }

    private void setKeyToPlayer(Room room,HashMap hashMap,String username){

        boolean checking=false;
        int key=0;
        do{
            key=generateRandomNumber(room.getPlayers().size());
            checking=doesKeyExist(hashMap,key);

        }while(checking==true);

        hashMap.put(key,username);
    }
    private int generateRandomNumber(int numberOfPLayers){
        Random random=new Random();
        return random.nextInt(numberOfPLayers)+1;
    }

    private boolean doesKeyExist(HashMap hashMap,int key){
        return (hashMap.containsKey(key))?true:false;
    }

    public  void sendToPlayers(Room room,HashMap hashMap,int onMovePosition) throws IOException, EncodeException {
        String username=(String)hashMap.get(onMovePosition);
        Command command=new Command("onMove",username);

        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
           s.getBasicRemote().sendObject(command);

        }




    }

}
