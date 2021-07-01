package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Jahrmarkt implements Cards {

    private final String NAME = "Jahrmarkt";
    private final int VALUE = 0;
    private final int COST = 5;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Jahrmarkt.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Jahrmarkt() {
        effectList.add("addMoney");
        effectList.add("addMoney");
        effectList.add("addAction");
        effectList.add("addAction");
        effectList.add("addBuy");
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