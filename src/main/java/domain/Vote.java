package domain;

public class Vote {
    private String username;
    private boolean accepted;

    public Vote(String username,boolean accepted){
        this.accepted=accepted;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}