package de.jonas.benogglserver.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import de.jonas.benogglserver.json.out.*;


public class JSONConverter {

    public static String toJSON(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static Object toObject(String message, Class obj) {
        Gson gson = new Gson();
        return gson.fromJson(message, obj);
    }

    public static Object toObject(JsonElement message, Class obj) {
        Gson gson = new Gson();
        return gson.fromJson(message, obj);
    }

    public static JsonElement toJSONElement(Object obj) {
        Gson gson = new Gson();
        return gson.toJsonTree(obj);
    }

    public static boolean isJSONValid(String jsonInString) {
        Gson gson = new Gson();
        try {
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    public static String getPayloadType(PacketOut packet) {
        if(packet instanceof JoinResponse) {
            return "joinResponse";
        } else if(packet instanceof GameFinish) {
            return "gameFinish";
        } else if(packet instanceof GameStart) {
            return "gameStart";
        } else if(packet instanceof LobbySnapshot) {
            return "lobbySnapshot";
        } else if(packet instanceof MeldenSnapshot) {
            return "meldenSnapshot";
        } else if(packet instanceof Next) {
            return "next";
        } else if(packet instanceof ReizenFinish) {
            return "reizenFinish";
        } else if(packet instanceof ReizenSnapshot) {
            return "reizenSnapshot";
        } else if(packet instanceof RoundFinish) {
            return "roundFinish";
        } else if(packet instanceof RoundStart) {
            return "roundStart";
        } else if(packet instanceof StechenSnapshot) {
            return "stechenSnapshot";
        }
        return null;
    }
}

