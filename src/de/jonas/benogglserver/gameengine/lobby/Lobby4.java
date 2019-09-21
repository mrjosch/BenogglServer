package de.jonas.benogglserver.gameengine.lobby;

import de.jonas.benogglserver.json.in.Action;
import de.jonas.benogglserver.json.in.JoinRequest;
import de.jonas.benogglserver.json.out.GameStart;
import de.jonas.benogglserver.json.out.JoinResponse;
import de.jonas.benogglserver.json.out.LobbySnapshot;
import de.jonas.benogglserver.json.out.Next;
import de.jonas.benogglserver.networking.Client;
import de.jonas.benogglserver.networking.Connection;
import de.jonas.benogglserver.networking.ConnectionListener;

public class Lobby4 extends Lobby {

    private static final int COUNTDOWN = 10; // in sec

    private int reizenPoints = 150;

    public Lobby4() {
        super("LOBBY4", 4, 8871);
        setConnectionListener(new ConnectionListener(this));
    }

    @Override
    public void handleAction(Action action, User user) {

    }

    @Override
    public void handleJoinRequest(JoinRequest request, Connection con) {
        // TODO is name not used yet?
        if (game.getUsers() < 4 && !game.isRunning()) {
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
        if (game.getUsers() == 4) {
            broadcast(new LobbySnapshot(game.getUser(0).getName(), game.getUser(1).getName(), game.getUser(2).getName(), game.getUser(3).getName(), getName()));
            game.start();
            for (int i = 0; i < game.getUsers(); i++) {
                User mUser = game.getUser(i);
                unicast(mUser,new GameStart(mUser.getPlayer().getHand(), COUNTDOWN, reizenPoints,game.getUser(0).getName(),game.getUser(1).getName(),game.getUser(2).getName(),game.getUser(3).getName(),new Next(game.getTurn().getName(),"reizen")));
            }
        } else if (game.getUsers() == 3) {
            broadcast(new LobbySnapshot(game.getUser(0).getName(), game.getUser(1).getName(), game.getUser(2).getName(), "", getName()));
        } else if (game.getUsers() == 2) {
            broadcast(new LobbySnapshot(game.getUser(0).getName(), game.getUser(1).getName(), "", "", getName()));
        } else {
            broadcast(new LobbySnapshot(game.getUser(0).getName(), "", "", "", getName()));
        }
    }
}
