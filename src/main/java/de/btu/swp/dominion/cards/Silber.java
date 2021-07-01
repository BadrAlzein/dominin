package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Silber implements Cards {

    private final String NAME = "Silber";
    private final int COST = 3;
    private final int VALUE = 2;
    private final String CARDTYPE = "Geld";
    private final String IMAGEPATH = "/src/main/resources/basis/Silber.png";
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