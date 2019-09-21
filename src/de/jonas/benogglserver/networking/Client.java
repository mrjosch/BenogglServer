package de.jonas.benogglserver.networking;

import de.jonas.benogglserver.json.Container;
import de.jonas.benogglserver.json.JSONConverter;
import de.jonas.benogglserver.json.in.Action;
import de.jonas.benogglserver.json.in.JoinRequest;
import de.jonas.benogglserver.json.in.PacketIn;
import de.jonas.benogglserver.json.out.PacketOut;

import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;

public class Client {

    private Connection con;

    public Client(Socket socket) {
        con = new Connection(socket);
    }

    public Client(Connection con) {
        this.con = con;
    }

    public void sendPackets(PacketOut ... packets) {
        for(PacketOut packet : packets) {
            sendPacket(packet);
        }
    }

    public void sendPacket(PacketOut packet) {
        Container container = new Container(new Timestamp(System.currentTimeMillis()).toString(),JSONConverter.getPayloadType(packet),JSONConverter.toJSONElement(packet));
        con.sendLine(JSONConverter.toJSON(container));
    }

    public PacketIn readPacket() throws IOException {
        return con.readPacket();
    }

    public void disconnect() {
        con.disconnect();
    }

    public boolean isTimeout(int timeout) {
        return con.isTimeout(timeout);
    }
}
