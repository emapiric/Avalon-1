package domain;

import java.util.Arrays;

public class Command {

    private String command;
    private String value;
    private String[] nominated;
   private boolean accepted;
   // private Vote [] votes;
   private String username;
   private Boolean[]votes;
   private String nameVotes[];

   public Command(String command,String[]nameVotes,Boolean [] votes){
       this.command=command;
       this.nameVotes=nameVotes;
       this.votes=votes;
   }

    public Boolean[] getVotes() {
        return votes;
    }

    public void setVotes(Boolean[] votes) {
        this.votes = votes;
    }

    public String[] getNameVotes() {
        return nameVotes;
    }

    public void setNameVotes(String[] nameVotes) {
        this.nameVotes = nameVotes;
    }

    /*public Command(String command,Vote[] votes){
            this.votes=votes;
        }*/
    public Command(String command, String value, String...nominated) {
        this.command = command;
        this.value = value;
        this.nominated = nominated;
    }

    public Command(String command, String value) {
        this.command = command;
        this.value = value;
    }
    public Command(String command, boolean accepted) {
        this.command = command;
        this.accepted = accepted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

   /* public Vote[] getVotes() {
        return votes;
    }

    public void setVotes(Vote[] votes) {
        this.votes = votes;
    }*/

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getNominated() {
        return nominated;
    }

    public void setNominated(String[] nominated) {
        this.nominated = nominated;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", value='" + value + '\'' +
                ", nominated=" + Arrays.toString(nominated) +
                '}';
    }
}
