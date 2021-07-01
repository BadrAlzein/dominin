package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Gaerten implements Cards {

    private final String NAME = "Gaerten";
    private final int VALUE = 0;
    private final int COST = 4;
    private final String CARDTYPE = "Punkte";
    private final String IMAGEPATH = "/src/main/resources/basis/Gaerten.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Gaerten() {
        effectList.add("addCardGarten");
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

    public ArrayList<String> getEffect() {
        return effectList;
    }

    public String getImagePath() {
        return this.IMAGEPATH;
    }
}