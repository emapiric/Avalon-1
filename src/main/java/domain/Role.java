package domain;

import javax.websocket.Session;

public class Role {

    private String role;
    private Session playerSession;

    public Role(String role, Session playerSession) {
        this.role = role;
        this.playerSession = playerSession;
    }

    public Role() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Session getPlayerSession() {
        return playerSession;
    }

    public void setPlayerSession(Session playerSession) {
        this.playerSession = playerSession;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                ", playerSession=" + playerSession +
                '}';
    }
}
