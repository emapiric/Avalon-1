package service.impl;


import LogicOfGame.OnMove.OnMove;
//import LogicOfGame.PlayersRoll.PlayersRollMetods;
import LogicOfGame.PlayersRoll.PlayersRollMetods;
import domain.Command;
import domain.Room;
import service.GameEndpointService;


import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class GameEndpointServiceImpl implements GameEndpointService {
    @Override
    public String Nominate(Room room, Command command) {
        return null;
    }

    @Override
    public String Vote(Room room, Command command) {
        return null;
    }

    @Override
    public String MissionVote(Room room, Command command) {
        return null;
    }

    @Override
    public String AssasinKill(Room room, Command command) {
        return null;
    }

    @Override
    public void setPlayersRoll(Room room) throws IOException, EncodeException {

        PlayersRollMetods playersRollMetods=new PlayersRollMetods();
        playersRollMetods.setPlayersRoll(room);

    }

    @Override
    public void sendPlayersWhoIsOnMove(Room room, HashMap<Integer, String> hashMap, int onCurrentMove) throws IOException, EncodeException {
        OnMove onMove=new OnMove();
        onMove.sendToPlayers(room,hashMap,onCurrentMove);
    }

    @Override
    public void setOnMove(Room room, HashMap hashMap) {

        OnMove onMove=new OnMove();
        onMove.setRandom(room,hashMap);
    }




}
