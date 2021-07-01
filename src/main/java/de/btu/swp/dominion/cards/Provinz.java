package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Provinz implements Cards {

    private final String NAME = "Provinz";
    private final int COST = 8;
    private final int VALUE = 6;
    private final String CARDTYPE = "Punkte";
    private final String IMAGEPATH = "/src/main/resources/basis/Provinz.png";
    private ArrayList<String> effectList = new ArrayList<String>();

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