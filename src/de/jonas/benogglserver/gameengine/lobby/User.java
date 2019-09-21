package de.jonas.benogglserver.gameengine.lobby;

import de.jonas.benogglserver.json.in.PacketIn;
import de.jonas.benogglserver.networking.Client;
import de.jonas.benogglserver.gameengine.model.Player;

import java.io.IOException;

public class User {

    private String name;
    private Player player;

    private Client client;

    public User(String name, Client client) {
        this.name = name;
        this.player = new Player(name);
        this.client = client;
    }

    public PacketIn readPacket() throws IOException {
        return client.readPacket();
    }

    public boolean isTimeout(int timeout) {
        return client.isTimeout(timeout);
    }

    public void disconnect() {
        client.disconnect();
    }


    // GETTER AND SETTER

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((User) obj).name);
    }
}
