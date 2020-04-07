package client;

import domain.Player;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.awt.*;
import java.net.URI;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Client {
    int playerId;


//ssss

    public static void main(String[] args) throws Exception {
        String Server="ws://localhost:9000/Avalon/Server/1/"+playerId();

        Scanner scanner=new Scanner(System.in);
        ClientManager clientManager=new ClientManager();
        Session session=clientManager.connectToServer(ClientEndpoint.class,new URI(Server));
        System.out.println("Ukucajte vas nickname");
        session.getBasicRemote().sendText(scanner.nextLine());
       // scanner.nextLine();
        while(true){

        }



    }

    public static int playerId(){
        Random random=new Random();
        return (int)random.nextInt(100);
    }


}
