package de.btu.swp.dominion.cards;

import java.util.ArrayList;

public interface Cards {
    public String getName();

    public int getCost();

    public int getPoints();

    public String getCardType();

    public ArrayList<String> getEffect();

    public String getImagePath();
}