package de.jonas.benogglserver.gameengine.lobby;

import de.jonas.benogglserver.gameengine.model.ReizenPhase;
import de.jonas.benogglserver.json.in.Action;
import de.jonas.benogglserver.json.in.JoinRequest;
import de.jonas.benogglserver.json.out.*;
import de.jonas.benogglserver.networking.Client;
import de.jonas.benogglserver.networking.Connection;
import de.jonas.benogglserver.networking.ConnectionListener;


public class Lobby2 extends Lobby {

    private static final int COUNTDOWN = 10; // in sec

    public Lobby2() {
        super("LOBBY2", 2, 8869);
        setConnectionListener(new ConnectionListener(this));
    }

    @Override
    public void handleAction(Action action, User user) {
            if(action.type.equals("reizen")) {
                ReizenPhase reizenPhase = (ReizenPhase) game.getRound().getPhase();
                if(action.overbid) {
                    reizenPhase.overBid(user);
                    broadcast(new ReizenSnapshot(reizenPhase.getScoreToMake()));
                } else {
                    reizenPhase.weg(user);
                }

                if(reizenPhase.checkWinner()) {
                    broadcast(new ReizenFinish(reizenPhase.getWinner().getName(),reizenPhase.getScoreToMake(),game.getRound().getDub()));
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                broadcast(new Next(reizenPhase.getWinner().getName(),"melden"));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                } else {
                    broadcast(new Next(reizenPhase.getNextUser().getName(),"reizen"));
                }
            } else if(action.type.equals("melden")) {

            } else if(action.type.equals("stechen")) {

            }
    }

    @Override
    public void handleJoinRequest(JoinRequest request, Connection con) {
        // TODO is name not used yet?
        if (game.getUsers() < 2 && !game.isRunning()) {
            User user = new User(request.name, new Client(con));
            System.err.println(getName() + " --- " + request.name + " joined");
            game.addUser(user);
            unicast(user, new JoinResponse(true));
            broadcastLobbySnapshot();
            pool.remove(con);
        } else {
            unicast(con, new JoinResponse(false));
        }
    }

    @Override
    public void broadcastLobbySnapshot() {
        if (game.getUsers() == 2) {
            broadcast(new LobbySnapshot(game.getUser(0).getName(), game.getUser(1).getName(), "", "", getName()));
            game.start();
            for (int i = 0; i < game.getUsers(); i++) {
                User mUser = game.getUser(i);
                unicast(mUser,new GameStart(mUser.getPlayer().getHand(), COUNTDOWN, game.getReizenStartScore(),game.getUser(0).getName(),game.getUser(1).getName(),null ,null,new Next(game.getTurn().getName(),"reizen")));
            }
        } else {
            broadcast(new LobbySnapshot(game.getUser(0).getName(), "", "", "", getName()));
        }
    }
}
