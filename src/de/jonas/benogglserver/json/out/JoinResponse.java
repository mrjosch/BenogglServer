package de.jonas.benogglserver.json.out;

public class JoinResponse implements PacketOut {

    public boolean successful;

    public JoinResponse(boolean successful) {
        this.successful = successful;
    }
}
