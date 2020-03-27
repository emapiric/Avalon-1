package server;

import endpoint.GameEndpoint;
import endpoint.RoomEndpoint;
import endpoint.ServerEndpoint;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class AvalonServer {

    public static List<domain.Room> rooms = new LinkedList<>();

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 9000, "/Avalon", ServerEndpoint.class, RoomEndpoint.class, GameEndpoint.class);

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            boolean stopServer = false;
            while(!stopServer){
                System.out.print("Please enter 'STOP' if you want to stop the server.\n\n");
                if(reader.readLine().equals("STOP"))
                    stopServer = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }

}
