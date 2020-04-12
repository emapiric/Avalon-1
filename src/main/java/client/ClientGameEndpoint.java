package client;

import com.google.gson.Gson;
import domain.Command;
import domain.Vote;
import domain.decoder.CommandDecoder;
import domain.decoder.PlayerDecoder;
import domain.encoder.CommandEncoder;
import domain.encoder.PlayerEncoder;

import javax.websocket.*;
import javax.websocket.ClientEndpoint;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

@javax.websocket.ClientEndpoint(decoders = CommandDecoder.class, encoders = CommandEncoder.class)
public class ClientGameEndpoint {
    private static Gson gson = new Gson();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    Scanner scanner = new Scanner(System.in);
    int missions = 1;

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {

        System.out.println("Prikacio si se ");

    }

    @OnMessage
    public void onMessage(Command command, Session session) throws IOException {

        switch (command.getCommand()) {
            case "roll":
                System.out.println("Your roll is " + command.getValue());

                System.out.println(command.getNominated());

                break;

            case "onMove":
                System.out.println("Na potezu je " + command.getValue());
                if (command.getValue().equals(session.getUserProperties().get("username"))) {
                    System.out.println("Ti si na potezu");
                    nominatedPlayersToMission(5,session);

                }
                break;

            case "nominated":
                Command command1=null;
                String []nominated=command.getNominated();
                    System.out.println("Nominovani su : ");
                for (String s:nominated) {
                    System.out.println(s);

                }

                System.out.println("Zelite li da nominovani podju na misiju? Ako zelite, napisite 1 a 0 u suprotnom");

                int odgovor=Integer.parseInt(scanner.nextLine());
                if(odgovor==1){
                   command1=new Command("vote",true);
                }
                else{
                    command1=new Command("vote",false);
                }
                try {
                    session.getBasicRemote().sendObject(command1);
                } catch (EncodeException e) {
                    e.printStackTrace();
                }


                break;

            case "nominatedVote":

                    showNominated(command.getNameVotes(),command.getVotes());


                break;


        }


    }


    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));

    }


    public void nominatedPlayersToMission( int numberOfPlayers,Session session) {
        switch (missions) {

            case 1:

        messageClient(missions,numberOfPlayers,session);
                break;


            case 2:

                break;


            case 3:

                break;

            case 4:


                break;

            case 5:

                break;


        }
    }

    public void messageClient(int missions, int numberOfPlayers, Session session) {
        Command command = null;

        //Dvoje saljem
        if ((missions==1 && numberOfPlayers>=5 && numberOfPlayers<=7) || (missions==3 && numberOfPlayers==1)) {
            System.out.println("Izaberite prvog igraca");
            String playerSend1 = scanner.nextLine();
            System.out.println("Izaberite drugog igraca");
            String playerSend2 = scanner.nextLine();
            command = new Command("nominated", null, playerSend1, playerSend2);

        }

        //Troje saljem

    /*    if((missions==2 || missions==4 || missions==5) ){
            System.out.println("Izaberite prvog igraca");
            String playerSend1=scanner.nextLine();
            System.out.println("Izaberite drugog igraca");
            String playerSend2=scanner.nextLine();
            System.out.println("Izaberite treceg igraca ");
            String playerSend3=scanner.nextLine();
            command=new Command("nominated",null,playerSend1,playerSend2,playerSend3);


        }

        //Cetvoro

        if(){


            if(){
                System.out.println("Izaberite prvog igraca");
                String playerSend1=scanner.nextLine();
                System.out.println("Izaberite drugog igraca");
                String playerSend2=scanner.nextLine();
                System.out.println("Izaberite treceg igraca ");
                String playerSend3=scanner.nextLine();
                System.out.println("Izaberite cetvrtog igraca ");
                String playerSend4=scanner.nextLine();
                command=new Command("nominated",null,playerSend1,playerSend2,playerSend3,playerSend4);


            }


            if(){
                System.out.println("Izaberite prvog igraca");
                String playerSend1=scanner.nextLine();
                System.out.println("Izaberite drugog igraca");
                String playerSend2=scanner.nextLine();
                System.out.println("Izaberite treceg igraca ");
                String playerSend3=scanner.nextLine();
                System.out.println("Izaberite cetvrtog igraca ");
                String playerSend4=scanner.nextLine();
                System.out.println("Izaberite petog igraca ");
                String playerSend5=scanner.nextLine();
                command=new Command("nominated",null,playerSend1,playerSend2,playerSend3,playerSend4,playerSend5);


            }*/
        try {
            session.getBasicRemote().sendObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }





    }

    public void showNominated(String[] nameVotes,Boolean[] votes){

        for (int i = 0; i <nameVotes.length ; i++) {
            System.out.println("Player "+nameVotes[i]+" voted "+votes[i]);
        }

    }

}
