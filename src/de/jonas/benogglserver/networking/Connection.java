package de.jonas.benogglserver.networking;

import de.jonas.benogglserver.json.Container;
import de.jonas.benogglserver.json.JSONConverter;
import de.jonas.benogglserver.json.in.Action;
import de.jonas.benogglserver.json.in.JoinRequest;
import de.jonas.benogglserver.json.in.PacketIn;
import de.jonas.benogglserver.json.out.PacketOut;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;

public class Connection {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private long time;
    private InputStream stream;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            stream = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            out = new PrintWriter(socket.getOutputStream(), true);
            time = System.currentTimeMillis();
        } catch (IOException e) {
            System.err.println("Error - Creating Connection");
        }
    }

    public void sendLine(String msg) {
        out.println(msg);
        System.out.println("Sent: " + msg);
    }

    public String readLine() throws IOException {
        String line;
        if(in.ready()){
            line = in.readLine();
            if(!line.contains(",\"payloadType\":\"ping\",\"payload\":{\"ping\":\"ping\"}}")){
                System.err.println("Received: " + line);
            }
            return line;
        }
        return null;
    }

    public PacketIn readPacket() throws IOException {
        String line = readLine();
        if(line != null && JSONConverter.isJSONValid(line)) {
            Container c = (Container) JSONConverter.toObject(line,Container.class);
            if(c.payloadType.equals("joinRequest")) {
                return (JoinRequest) JSONConverter.toObject(c.payload,JoinRequest.class);
            } else if(c.payloadType.equals("action")) {
                return (Action) JSONConverter.toObject(c.payload,Action.class);
            }
            time = System.currentTimeMillis();
        }
        return null;
    }

    public void sendPacket(PacketOut packet) {
        Container container = new Container(new Timestamp(System.currentTimeMillis()).toString(),JSONConverter.getPayloadType(packet),JSONConverter.toJSONElement(packet));
        sendLine(JSONConverter.toJSON(container));
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isTimeout(int timeout) {
        return System.currentTimeMillis() > time + timeout;
    }

    // GETTER AND SETTER

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Socket getSocket() {
        return socket;
    }

}
