package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Abenteurer implements Cards {

    private final String NAME = "Abenteurer";
    private final int VALUE = 0;
    private final int COST = 6;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Abenteurer.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Abenteurer() {
        effectList.add("addAdventure");
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