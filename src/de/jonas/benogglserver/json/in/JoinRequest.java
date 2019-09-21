package de.jonas.benogglserver.json.in;

public class JoinRequest implements PacketIn {

    public String name;

    public JoinRequest(String name) {
        this.name = name;
    }
}
