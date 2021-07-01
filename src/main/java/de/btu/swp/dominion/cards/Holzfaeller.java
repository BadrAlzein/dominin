package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Holzfaeller implements Cards {

    private final String NAME = "Holzfaeller";
    private final int COST = 3;
    private final int VALUE = 0;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Holzfaeller.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Holzfaeller() {
        effectList.add("addBuy");
        effectList.add("addMoney");
        effectList.add("addMoney");
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