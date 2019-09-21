package de.jonas.benogglserver.gameengine.model;

import de.jonas.benogglserver.commons.Cardname;
import de.jonas.benogglserver.commons.Cardtype;

public class Card {

    private Cardtype type;
    private Cardname name;

    public Card(Cardtype type, Cardname name) {
        this.type = type;
        this.name = name;
    }

    public Cardtype getType() {
        return type;
    }

    public Cardname getName() {
        return name;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Card card = (Card) obj;
        return this.type.equals(card.type) && this.name.equals(card.name);
    }
}
