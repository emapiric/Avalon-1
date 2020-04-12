package client;

import domain.Player;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.websockets.WebSocket;

import javax.websocket.Session;
import java.awt.*;
import java.net.URI;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Client {
    int playerId;




    public static void main(String[] args) throws Exception {



        Client client=new Client();
        Scanner scanner=new Scanner(System.in);
        client.playerId=playerId();

        String Server="ws://localhost:9000/Avalon/Server/1/"+client.playerId;


        ClientManager clientManager=new ClientManager();
        Session session=clientManager.connectToServer(ClientEndpoint.class,new URI(Server));
        System.out.println("Ukucajte vas nickname");

        String userName=scanner.nextLine();
        session.getBasicRemote().sendText(userName);



        session.getUserProperties().put("end","null");
        Thread.sleep(18000);
        while(true){

            if(!session.getUserProperties().get("end").equals("null")){
                String message=session.getUserProperties().get("end").toString();
                if(message.equals("Usli ste u gameThread")){
                    System.out.println("IZASAO IZ ROOMENDPOINT SERVER");
                    break;
                }

            }
        }

        System.out.println("Cekam drugo");
        session.close();

        Server="ws://localhost:9000/Avalon/Server/1/"+client.playerId+"/Game";

        Session session1=clientManager.connectToServer(EnteredEndpoint.class,new URI(Server));

        session1.getUserProperties().put("username",userName);



        while(true){

        }


    }

    public static int playerId(){
        Random random=new Random();
          return  random.nextInt(100);
    }


}
