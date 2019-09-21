package de.jonas.benogglserver.gameengine.model;

import de.jonas.benogglserver.gameengine.lobby.User;

import java.util.ArrayList;

public class ReizenPhase extends Phase {

    private ArrayList<User> orderedUsers;

    private User lastOverBid;

    private User winner;
    private int scoreToMake;

    public ReizenPhase(ArrayList<User> orderedUsers, int scoreToMake) {
        this.orderedUsers = orderedUsers;
        this.scoreToMake = scoreToMake;
        lastOverBid = orderedUsers.get(0);
    }

    public void overBid(User user) {
        scoreToMake+=10;
        User first = orderedUsers.get(0);
        User second = orderedUsers.get(1);

        lastOverBid = user;

        orderedUsers.clear();
        orderedUsers.add(0,second);
        orderedUsers.add(1,first);
    }

    public void weg(User user) {
        orderedUsers.remove(user);
    }

    public boolean checkWinner() {
        if(orderedUsers.size() == 1) {
            winner = orderedUsers.get(0);
            return true;
        }
        return false;
    }

    public User getWinner(){
        return winner;
    }

    public int getScoreToMake() {
        return scoreToMake;
    }

    public User getNextUser() {
        System.out.println(orderedUsers.size());
        for (int i = 0; i < orderedUsers.size(); i++) {
            System.out.println(orderedUsers.get(i).getName());
        }

        for (int i = 0; i < 2 ; i++) {
            if(!orderedUsers.get(i).equals(lastOverBid)) {
                System.out.println("NEXT " +orderedUsers.get(i).getName());
                return orderedUsers.get(i);
            }
        }
        return null;
    }
}
