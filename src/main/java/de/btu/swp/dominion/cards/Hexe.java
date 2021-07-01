package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Hexe implements Cards {

    private final String NAME = "Hexe";
    private final int VALUE = 0;
    private final int COST = 5;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Hexe.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Hexe() {
        effectList.add("addHexeEffect");
    }

    public String getName() {
        return this.NAME;
    }

    public int getCost() {
        return this.COST;
    }

    public int getPoints() {
        return this.VALUE;
    }

    public String getCardType() {
        return this.CARDTYPE;
    }

    public String getImagePath() {
        return this.IMAGEPATH;
    }

    public ArrayList<String> getEffect() {
        return this.effectList;
    }
}