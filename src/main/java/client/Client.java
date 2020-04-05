package client;

import domain.Player;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.net.URI;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Client {
    int playerId;




    public static void main(String[] args) throws Exception {
        String Server="ws://localhost:9000/Avalon/Server/1/"+playerId();

        ClientManager clientManager=new ClientManager();
        Session session=clientManager.connectToServer(ClientEndpoint.class,new URI(Server));


        while(true){

        }



    }

    public static int playerId(){
        Random random=new Random();
        return (int)random.nextInt(100);
    }


}
