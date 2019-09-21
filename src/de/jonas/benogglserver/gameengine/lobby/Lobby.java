package de.jonas.benogglserver.gameengine.lobby;

import de.jonas.benogglserver.json.in.Action;
import de.jonas.benogglserver.json.in.JoinRequest;
import de.jonas.benogglserver.json.in.PacketIn;
import de.jonas.benogglserver.json.out.*;
import de.jonas.benogglserver.gameengine.model.Game;
import de.jonas.benogglserver.networking.Connection;
import de.jonas.benogglserver.networking.ConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public abstract class Lobby {

    private final static int TIMEOUT = 10000;

    private String name;
    private ServerSocket serverSocket;
    private ConnectionListener connectionListener;

    protected ArrayList<Connection> pool;             // Contains Clients which didnt send joinRequest

    protected Game game;

    public Lobby(String name, int size, int port) {
        try {
            this.name = name;
            this.serverSocket = new ServerSocket(port);
            pool = new ArrayList<>();
            this.game = new Game(size);
        } catch (IOException e) {
            System.err.println("Couldnt create Lobby " + name);
        }
    }


    // ABSTRACT METHODS

    public abstract void handleAction(Action action, User user);

    public abstract void handleJoinRequest(JoinRequest request, Connection con);

    public abstract void broadcastLobbySnapshot();

    // METHODS

    public void start() {
        run();
    }

    private void run() {
        Thread lobby = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Started " + name);
                System.out.println();
                while (true) {
                    try {
                        if (game.getUsers() != 0) {
                            for (int i = 0; i < game.getUsers(); i++) {
                                User mUser = game.getUser(i);
                                if (!isTimeout(mUser, TIMEOUT)) {
                                    try {
                                        PacketIn packet = game.getUser(i).readPacket();
                                        if (packet != null) {
                                            if (packet instanceof Action) {
                                                handleAction((Action) packet, mUser);
                                            }
                                        }
                                    } catch (IOException e) {
                                        System.err.println("Lobby - run() IOException1");
                                    }
                                } else {
                                    System.err.println(getName() + " --- " + mUser.getName() + " timed out");
                                    removeUser(game.getUser(i));
                                    game.reset();
                                    for (int j = 0; j < game.getUsers(); j++) {
                                        if(!game.isRunning()) {
                                            broadcastLobbySnapshot();
                                        }
                                    }
                                }
                            }
                        }


                        if (pool.size() != 0) {
                            for (int i = 0; i < pool.size(); i++) {
                                Connection mCon = pool.get(i);
                                if (!mCon.isTimeout(TIMEOUT)) {
                                    try {
                                        PacketIn packet = mCon.readPacket();
                                        if (packet != null) {
                                            if (packet instanceof JoinRequest) {
                                                handleJoinRequest((JoinRequest) packet, mCon);
                                            }
                                        }
                                    } catch (IOException e) {
                                        System.err.println("Lobby - run() IOException1");
                                    }
                                } else {
                                    System.err.println(getName() + " ---" + "A client timed out");
                                    pool.remove(i);
                                }
                            }
                        }
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        lobby.run();
    }

    public void broadcast(PacketOut... packets) {
        for (PacketOut packet : packets) {
            broadcast(packet);
        }
    }

    public void broadcast(PacketOut packet) {
        for (int i = 0; i < game.getUsers(); i++) {
            game.getUser(i).getClient().sendPacket(packet);
        }
    }

    public void unicast(User receiver, PacketOut... packets) {
        for (PacketOut packet : packets) {
            unicast(receiver, packet);
        }
    }

    public void unicast(User receiver, PacketOut packet) {
        receiver.getClient().sendPacket(packet);
    }

    public void unicast(Connection receiver, PacketOut packet) {
        receiver.sendPacket(packet);
    }


    public void addConnection(Connection con) {
        pool.add(con);
    }

    public void removeUser(User user) {
        game.removeUser(user);
        closeConnection(user);
    }

    private boolean isTimeout(User user, int timeout) {
        return user.isTimeout(timeout);
    }

    private void closeConnection(User user) {
        user.disconnect();
    }


    // GETTER AND SETTER

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ArrayList<Connection> getPool() {
        return pool;
    }
}
