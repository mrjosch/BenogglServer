package de.jonas.benogglserver.networking;

import de.jonas.benogglserver.gameengine.lobby.Lobby;

import java.io.IOException;
import java.net.Socket;

public class ConnectionListener extends Thread{

    private Lobby lobby;

    public ConnectionListener(Lobby lobby) {
        this.lobby = lobby;
        start();
    }

    public void run() {
        Thread connectHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                Connection con;
                System.out.println("Connection Handler " + lobby.getName() + " started");
                while (true) {
                    try {
                        socket = lobby.getServerSocket().accept();
                        con = new Connection(socket);
                        System.err.println(lobby.getName() + " --- A client connected");
                        lobby.addConnection(con);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        connectHandler.start();
    }
}
