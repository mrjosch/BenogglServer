package de.jonas.benogglserver.gameengine.model;


import de.jonas.benogglserver.commons.Cardtype;
import de.jonas.benogglserver.commons.Comboname;

public class Combo {

    private Cardtype type;
    private Comboname name;

    public Combo(Cardtype type, Comboname name) {
        this.type = type;
        this.name = name;
    }

    public Cardtype getType() {
        return type;
    }

    public Comboname getName() {
        return name;
    }
}
