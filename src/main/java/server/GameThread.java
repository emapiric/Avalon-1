package server;

import domain.Room;

public class GameThread extends Thread{

    private Room room;

    public GameThread(Room room) {
        this.room = room;
    }

    public void run(){
        /**
         *
         * LOGIKA IGRICE
         *
         * Poslati svima koji su likovi
         * Prvi igrac daje predlog. server dobija command objekat sa listom ljudi koje je on predlozio
         * Ako je 4. predlog onda se bira izmedju 4 i 5 igraca
         *
         * Vise od pola dalo DA onda se glasa za misiju =>
         *
         */
    }

}
