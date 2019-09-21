package de.jonas.benogglserver.json.out;

public class LobbySnapshot implements PacketOut {

    public String player1Name;
    public String player2Name;
    public String player3Name;
    public String player4Name;
    public String lobbyName;

    public LobbySnapshot(String player1Name, String player2Name, String player3Name, String player4Name, String lobbyName) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player3Name = player3Name;
        this.player4Name = player4Name;
        this.lobbyName = lobbyName;
    }
}

