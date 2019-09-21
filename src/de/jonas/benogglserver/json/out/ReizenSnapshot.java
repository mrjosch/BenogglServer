package de.jonas.benogglserver.json.out;

public class ReizenSnapshot implements PacketOut {

    public int points;

    public ReizenSnapshot(int points) {
        this.points = points;
    }
}
