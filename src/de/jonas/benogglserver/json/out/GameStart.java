package de.jonas.benogglserver.json.out;

import de.jonas.benogglserver.gameengine.model.Card;

import java.util.ArrayList;

public class GameStart implements PacketOut {

    public ArrayList<Card> cards;
    public int countdown;
    public int reizenStartPoints;
    public String namePlayer1;
    public String namePlayer2;
    public String namePlayer3;
    public String namePlayer4;
    public Next firstNext;

    public GameStart(ArrayList<Card> cards, int countdown, int reizenStartPoints, String namePlayer1, String namePlayer2, String namePlayer3, String namePlayer4, Next firstNext) {
        this.cards = cards;
        this.countdown = countdown;
        this.reizenStartPoints = reizenStartPoints;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        this.namePlayer3 = namePlayer3;
        this.namePlayer4 = namePlayer4;
        this.firstNext = firstNext;
    }
}
