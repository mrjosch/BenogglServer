package de.jonas.benogglserver.gameengine.model;

import de.jonas.benogglserver.commons.Cardname;
import de.jonas.benogglserver.commons.Cardtype;
import de.jonas.benogglserver.gameengine.lobby.User;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;


public class Game {

    private int reizenStartScore;

    private int size;
    private ArrayList<User> users;
    private ArrayList<Card> deck;

    private int turnIndex;
    private Round round;

    private boolean running;

    public Game(int size) {
        reizenStartScore = 140;
        this.size = size;
        users = new ArrayList<>();
        deck = initDeck();
        turnIndex = 0;
        running = false;
    }

    public void start() {
        running = true;
        newRound();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void newRound() {
        round = new Round(this, turnIndex, reizenStartScore);
        turnIndex = (turnIndex + 1) % size;
    }

    public User getWinner() {
        for (User user : users) {
            Player player = user.getPlayer();
            if (player.getScore() > 1000) {
                running = false;
                return user;
            }
        }
        return null;
    }

    public ArrayList<Card> getDeckCopie() {
        ArrayList<Card> rDeck = new ArrayList<>();
        for (Card card : deck) {
            rDeck.add(card);
        }
        return rDeck;
    }

    public User getTurn(){
        if(running){
            return users.get(round.getTurnIndex());
        }
        return null;
    }

    private ArrayList<Card> initDeck() {
        ArrayList<Card> mDeck = new ArrayList<>();

        Cardtype[] types = {Cardtype.BLATT, Cardtype.EICHEL, Cardtype.HERZ, Cardtype.SHELL};

        if(size == 2 | size == 3) {
            Cardname[] names = {Cardname.ASS, Cardname.ZEHN, Cardname.KÖNIG, Cardname.OBER,Cardname.UNTER};
            for(Cardtype mType : types) {
                for (int i = 0; i < 2; i++) {
                    for(Cardname mName : names) {
                        mDeck.add(new Card(mType, mName));
                    }
                }
            }
        } else if(size == 4) {
            Cardname[] names = {Cardname.ASS, Cardname.ZEHN, Cardname.KÖNIG, Cardname.OBER,Cardname.UNTER, Cardname.SIEBEN};
            for(Cardtype mType : types) {
                for (int i = 0; i < 2; i++) {
                    for(Cardname mName : names) {
                        mDeck.add(new Card(mType, mName));
                    }
                }
            }
        }

        return null;
    }


   /* private ArrayList<Card> loadDeckFromDB(int playerCount) {
        HSQLDatabase db = new HSQLDatabase("database/cards");
        if (playerCount == 2 || playerCount == 3) {
            try {
                db.connect();
                ResultSet resultSet = db.query("SELECT* FROM players_3");

                ArrayList<Card> deck = new ArrayList<>();
                while (resultSet.next()) {
                    String cardType = resultSet.getString(1);
                    String cardName = resultSet.getString(2);
                    deck.add(new Card(Cardtype.valueOf(cardType), Cardname.valueOf(cardName)));
                }

                db.disconnect();
                return deck;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (playerCount == 4) {
            try {
                db.connect();
                ResultSet resultSet = db.query("SELECT* FROM players_4");

                ArrayList<Card> deck = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                while (resultSet.next()) {
                    String cardType = resultSet.getString(1);
                    String cardName = resultSet.getString(2);
                    deck.add(new Card(Cardtype.valueOf(cardType), Cardname.valueOf(cardName)));
                }

                db.disconnect();
                return deck;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }*/

    public void reset() {
        running = false;
        turnIndex = 0;
        round = null;
    }

    // GETTER AND SETTER

    public Round getRound() {
        return round;
    }

    public User getUser(int index) {
        return users.get(index);
    }

    public int getUserIndex(User user) {
        return users.indexOf(user);
    }

    public boolean isRunning() {
        return running;
    }

    public int getSize() {
        return size;
    }

    public int getUsers() {
        return users.size();
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public int getReizenStartScore() {
        return reizenStartScore;
    }
}
