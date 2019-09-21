package de.jonas.benogglserver.json.out;

import de.jonas.benogglserver.gameengine.model.Card;

import java.util.ArrayList;

public class ReizenFinish implements PacketOut {

    public String nameWinner;
    public int pointsToMake;
    public ArrayList<Card> dub;

    public ReizenFinish(String nameWinner, int pointsToMake, ArrayList<Card> dub) {
        this.nameWinner = nameWinner;
        this.pointsToMake = pointsToMake;
        this.dub = dub;
    }
}
