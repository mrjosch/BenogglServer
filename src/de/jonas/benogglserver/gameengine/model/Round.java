package de.jonas.benogglserver.gameengine.model;

import de.jonas.benogglserver.gameengine.lobby.User;

import java.util.ArrayList;
import java.util.Random;

public class Round {

    private Game game;
    private ArrayList<Card> dub;

    private int turnIndex;

    private Phase phase;


    public Round(Game game, int firstTurnIndex, int reizenStartScore) {
        this.game = game;
        turnIndex = firstTurnIndex;
        dub = new ArrayList<>();
        giveCards();

        ArrayList<User> orderedUsers = new ArrayList<>();
        for (int i = 0; i < game.getSize() ; i++) {
            orderedUsers.add(game.getUser((firstTurnIndex+i)%game.getSize()));
        }

        phase = new ReizenPhase(orderedUsers,reizenStartScore);
    }

    private void giveCards() {
        Random rand = new Random();
        ArrayList<Card> deck = game.getDeckCopie();

        for (int i = 0; i < 4; i++) {
            Card card = deck.get(rand.nextInt(deck.size()));
            deck.remove(card);
            dub.add(card);
        }

        for (int i = 0; i < game.getUsers() ; i++) {
            Player player = game.getUser(i).getPlayer();
            for (int j = 0; j < 12; j++) {
                Card card = deck.get(rand.nextInt(deck.size()));
                deck.remove(card);
                player.addCardToHand(card);
            }
        }
    }


    // GETTER AND SETTER

    public ArrayList<Card> getDub() {
        return dub;
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public Phase getPhase() {
        return phase;
    }
}
