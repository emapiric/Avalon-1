package client;

import domain.PlayerDecoder;
import domain.PlayerEncoder;

import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@javax.websocket.ClientEndpoint()
public class EnteredEndpoint {

    private static CountDownLatch latch;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        logger.info("Connected to room " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println(message);
    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
        latch.countDown();
    }

}
