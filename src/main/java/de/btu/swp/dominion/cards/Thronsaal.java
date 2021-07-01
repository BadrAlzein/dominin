package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Thronsaal implements Cards {

    private final String NAME = "Thronsaal";
    private final int VALUE = 0;
    private final int COST = 4;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Thronsaal.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Thronsaal() {
        effectList.add("addThronsaalEffect");
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