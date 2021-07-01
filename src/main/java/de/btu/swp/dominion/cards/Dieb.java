package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Dieb implements Cards {

    private final String NAME = "Dieb";
    private final int VALUE = 0;
    private final int COST = 4;
    private final String CARDTYPE = "Aktion";
    private final String IMAGEPATH = "/src/main/resources/basis/Dieb.png";
    private ArrayList<String> effectList = new ArrayList<String>();

    public Dieb() {
        effectList.add("addDiebEffekt");
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