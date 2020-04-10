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



    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("server send: " + message);

        if(message.equals("startGame")){
            session.getUserProperties().put("end","Usli ste u gameThread");


        }



    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
        latch.countDown();
    }

}