package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Blanko implements Cards {

    private final String NAME = "Blanko";
    private final int COST = 0;
    private final int VALUE = 0;
    private final String CARDTYPE = "Blanko";
    private final String IMAGEPATH = "/src/main/resources/basis/Blanko.png";
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