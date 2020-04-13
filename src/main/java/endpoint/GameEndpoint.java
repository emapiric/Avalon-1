package endpoint;

import domain.Command;
import domain.Room;
import domain.decoder.CommandDecoder;
import domain.decoder.PlayerDecoder;
import domain.encoder.CommandEncoder;
import domain.encoder.PlayerEncoder;
import server.GameThread;
import service.GameEndpointService;
import service.RoomEndpointService;
import service.ServerEndpointService;
import service.impl.GameEndpointServiceImpl;
import service.impl.RoomEndpointServiceImpl;
import service.impl.ServerEndpointServiceImpl;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@ServerEndpoint(value = "/Server/{roomId}/{playerId}/Game", decoders = CommandDecoder.class, encoders = CommandEncoder.class)
public class GameEndpoint {


    private Logger logger = Logger.getLogger(this.getClass().getName());
    public GameEndpointService gameEndpointService = new GameEndpointServiceImpl();
    public ServerEndpointService serverEndpointService= new ServerEndpointServiceImpl();
    public static Set<Room> rooms = endpoint.ServerEndpoint.rooms;

    public int nesto=0;

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) throws IOException {
//        logger.info("Connected in room: " + roomId);

            newSession(roomId,playerId,session);
            if(allConnected(serverEndpointService.findRoom(roomId,rooms).getPlayers())){
                GameThread.playersConnected = true;
                System.out.println("Postavio ga");
            }
    }
    //

    @OnMessage
    public void onMessage(Command command, Session session, @PathParam("roomId") String roomId, @PathParam("playerId") String playerId) {

        switch(command.getCommand()){
            case "nominated":
                serverEndpointService.findRoom(roomId,rooms).setNominated(command.getNominated());

                sendNominationToAllPlayers(command.getNominated(),roomId);
                serverEndpointService.findRoom(roomId,rooms).voteInMission.setPlayerInMission(command.getNominated().length);
              //  serverEndpointService.findRoom(roomId,rooms).setOnMovePlayer(true);

                break;

            case "vote":

                serverEndpointService.findRoom(roomId,rooms).voteForMission.setVotes(command.isAccepted());
                serverEndpointService.findRoom(roomId,rooms).voteForMission.setVoteNames(session.getUserProperties().get("username").toString());
                int valueForVote=serverEndpointService.findRoom(roomId,rooms).voteForMission.setVotesNumberAndOthers();

                if(valueForVote==1){
                    serverEndpointService.findRoom(roomId,rooms).setOnMovePlayer(true);
                }
                    System.out.println(valueForVote);

                break;

            case "voteInMission":

                serverEndpointService.findRoom(roomId,rooms).voteInMission.setVotes(command.isAccepted());
                serverEndpointService.findRoom(roomId,rooms).voteInMission.setVoteNames(session.getUserProperties().get("username").toString());
                int valueForMission=serverEndpointService.findRoom(roomId,rooms).voteInMission.setVotesNumberAndOthers();
                    if(valueForMission==0){
                        if(serverEndpointService.findRoom(roomId,rooms).voteInMission.getQuest()>=3 && serverEndpointService.findRoom(roomId,rooms).voteInMission.getGood()==3){
                            Command command1=new Command("gameOver","Good");
                            break;
                        }
                        if(serverEndpointService.findRoom(roomId,rooms).voteInMission.getQuest()>=3 && serverEndpointService.findRoom(roomId,rooms).voteInMission.getEvil()==3){
                            Command command1=new Command("gameOver","Evil");
                            break;
                        }
                        serverEndpointService.findRoom(roomId,rooms).setOnMovePlayer(true);

                    }
                break;
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    public boolean allConnected(Set<Session> players){
        for (Iterator<Session> it = players.iterator(); it.hasNext(); ) {
            Session s = it.next();
                if(s.getUserProperties().get("connected").equals("false")){
                    return false;
                }
            }
        logger.info("SIBAJ DALJE!");
        return true;
    }

    public  void newSession(String roomId, String playerId,Session session) {
        Room room = serverEndpointService.findRoom(roomId,rooms);
        if(room == null)
            System.out.println("mrtvi room null");
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();


            System.out.println(s.getUserProperties().get("playerId") + " igrac");
            if (s.getUserProperties().get("playerId").equals(playerId)){
                System.out.println("Menjam session"+playerId);
                String username=s.getUserProperties().get("username").toString();

                session.getUserProperties().put("username",username);
                session.getUserProperties().put("roomId",roomId);
                session.getUserProperties().put("playerId",playerId);
                session.getUserProperties().put("connected","true");
                room.getPlayers().remove(s);
                room.getPlayers().add(session);

                return;
            }
        }
    }

    public void sendNominationToAllPlayers(String[] nominated,String roomId){
        Room room = serverEndpointService.findRoom(roomId,rooms);
        Command command=new Command("nominated",null,nominated);
        if(room == null)
            System.out.println("mrtvi room null");
        for (Iterator<Session> it = room.getPlayers().iterator(); it.hasNext(); ) {
            Session s = it.next();
            try {
                s.getBasicRemote().sendObject(command);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }

        }

    }

}
