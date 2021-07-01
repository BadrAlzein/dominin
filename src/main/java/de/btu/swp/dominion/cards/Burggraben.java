package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Burggraben implements Cards {

    private final String NAME = "Burggraben";
    private final int VALUE = 0;
    private final int COST = 2;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Burggraben.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Burggraben() {
        effectList.add("addCard");
        effectList.add("addCard");
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