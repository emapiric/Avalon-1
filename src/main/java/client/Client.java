package client;

import domain.Player;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Client {

    public static final String SERVER = "ws://localhost:9000/Avalon/Server/3022/1231/Game";

    public static void main(String[] args) throws Exception {




//        final CountDownLatch startSignal = new CountDownLatch(1);
////        final CountDownLatch doneSignal = new CountDownLatch(10);
////
////        ClientManager client = ClientManager.createClient();
////        ClientManager client1 = ClientManager.createClient();
////        String message;
////
////        // connect to server
////        Scanner scanner = new Scanner(System.in);
////        System.out.println("Welcome player!");
////
////        System.out.println("What's your name?");
////        String user = scanner.nextLine();
////
////        System.out.println("PlayerId?");
////        String f = scanner.nextLine();
////
////        String playerID = null;
////        String roomID = null;
////        if(f.equals("yes")) {
////            playerID = scanner.nextLine();
////        }
////
////        System.out.println("RoomId?");
////        f = scanner.nextLine();
////
////        if(f.equals("yes")) {
////            roomID = scanner.nextLine();
////        }
////
////
////        Session session = client.connectToServer(ClientEndpoint.class, new URI(SERVER));
////        System.out.println("You are logged in as: " + user);
////
////        domain.Player player = new Player(user,playerID,roomID);
////
////        session.getBasicRemote().sendObject(player);
////
////        do {
////            player = (Player) session.getUserProperties().get("player");
////        }while(player == null);
////        session.close();
////
//////        System.out.println("new player: " + player);
////
////        String uri = SERVER + "/" + player.getRoomId();
////        session= client1.connectToServer(EnteredEndpoint.class, new URI(uri));
////
//////        while(true){
//////
//////        }
////
////        do {
////            message = scanner.nextLine();
////            session.getBasicRemote().sendText(message);
////        } while (!message.equalsIgnoreCase("quit"));
//////
//////        do {
//////            message = scanner.nextLine();
//////            session.getBasicRemote().sendText(message);
//////        } while (!message.equalsIgnoreCase("quit"));
    }

}
