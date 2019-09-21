package de.jonas.benogglserver.json.in;

import de.jonas.benogglserver.gameengine.model.Card;
import de.jonas.benogglserver.gameengine.model.Combo;

import java.util.ArrayList;

public class Action implements PacketIn {

    public String type;

    public Boolean overbid;
    public ArrayList<Combo> cardCombos;
    public Card card;

    public Action(String type, Boolean overbid, ArrayList<Combo> cardCombos, Card card) {
        this.type = type;
        this.overbid = overbid;
        this.cardCombos = cardCombos;
        this.card = card;
    }
}
