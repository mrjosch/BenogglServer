package de.jonas.benogglserver.json;

import com.google.gson.JsonElement;

public class Container {

    public String timestamp;
    public String payloadType;
    public JsonElement payload;

    public Container() {

    }

    public Container(String timestamp, String payloadType, JsonElement payload) {
        this.timestamp = timestamp;
        this.payloadType = payloadType;
        this.payload = payload;
    }

}
