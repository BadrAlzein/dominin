package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public class Kapelle implements Cards {

    private final String NAME = "Kapelle";
    private final int COST = 2;
    private final int POINT = 0;
    private final String CARDTYPE = "Aktion";
    private ArrayList<String> effectList = new ArrayList<String>();
    private final String IMAGEPATH = "/src/main/resources/basis/Kapelle.png";

    public Kapelle() {

        effectList.add("addKapelleEffect");
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public int getPoints() {
        return POINT;
    }

    @Override
    public String getCardType() {
        return CARDTYPE;
    }

    @Override
    public ArrayList<String> getEffect() {
        return effectList;
    }

    @Override
    public String getImagePath() {
        return IMAGEPATH;
    }
}
