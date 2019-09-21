package de.jonas.benogglserver.networking;

import de.jonas.benogglserver.gameengine.lobby.Lobby2;
import de.jonas.benogglserver.gameengine.lobby.Lobby3;
import de.jonas.benogglserver.gameengine.lobby.Lobby4;

public class BenogglServer {

    public static void main(String[] args) {
        BenogglServer server = new BenogglServer();
        server.start();
    }

    private Lobby2 lobby2;
    private Lobby3 lobby3;
    private Lobby4 lobby4;

    public BenogglServer() {
            lobby2 = new Lobby2();
            lobby3 = new Lobby3();
            lobby4 = new Lobby4();
    }

    public void start() {
        Thread l2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lobby2.start();
            }
        });

        Thread l3 = new Thread(new Runnable() {
            @Override
            public void run() {
                lobby3.start();
            }
        });

        Thread l4 = new Thread(new Runnable() {
            @Override
            public void run() {
                lobby4.start();
            }
        });

        l2.start();
        l3.start();
        l4.start();
    }

    public void stop() {
        // TODO
    }

}
