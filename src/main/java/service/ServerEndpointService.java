package service;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;

public interface ServerEndpointService {

    /*Sends:    *Reconnect
                *Error sending player
                *Wrong username
                *OK => everything is ok and send player obj
     */
    public String allocate(domain.Player player, Map<String, Set<Session>> activeRooms, Session session);

    public void newPlayer(domain.Player player, Map<String, Set<Session>> activeRooms, Session session);

}
