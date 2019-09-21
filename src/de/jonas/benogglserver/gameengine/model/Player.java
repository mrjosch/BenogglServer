package de.jonas.benogglserver.gameengine.model;

import java.util.ArrayList;

public class Player {

    private String name;
    private int score;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        score = 0;
        hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}
