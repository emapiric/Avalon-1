package client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.*;

import domain.decoder.PlayerDecoder;
import domain.encoder.PlayerEncoder;

@javax.websocket.ClientEndpoint(decoders = PlayerDecoder.class, encoders = PlayerEncoder.class)
public class ClientEndpoint {

    private static CountDownLatch latch;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        logger.info("Connected ... " + session.getId());
//
//
//
//        session.getBasicRemote().sendText("Milos,123");
//        try {
//            session.getBasicRemote().sendText("start");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @OnMessage
    public void onMessage(domain.Player player, Session session) throws IOException {
        System.out.println("server send: " + player);
        session.getUserProperties().put("player",player);
//        System.out.println("Unesi: ");
//        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            logger.info("Received ...." + message);
//
//            String command = bufferRead.readLine();
//            session.getBasicRemote().sendText(command);
//            if(!command.equals("add")){
//                return command;
//            }
//            System.out.println("Username: ");
//            String userName = bufferRead.readLine();
//            System.out.println("id");
//            String id = bufferRead.readLine();
//
//            main.java.domain.Player player = new Player(userName,Integer.parseInt(id));
//            session.getBasicRemote().sendObject(player);
//
//        } catch (IOException | EncodeException e) {
//            throw new RuntimeException(e);
//        }
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
        latch.countDown();
    }

}