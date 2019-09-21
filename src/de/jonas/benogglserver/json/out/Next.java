package de.jonas.benogglserver.json.out;

public class Next implements PacketOut {

    public String name;
    public String type;

    public Next(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
