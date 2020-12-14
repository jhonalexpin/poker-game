package com.zooker.poker.model;

import com.zooker.poker.enums.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Player {

    private String playerName;
    private List<Card> cards;
    private TreeMap<Rank, List<Card>> hand;

    public Player(String playerName) {
        this.playerName = playerName;
        this.cards = new ArrayList<>();
        this.hand = new TreeMap<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Card> getCards() {
        return cards;
    }

    public TreeMap<Rank, List<Card>> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", hand=" + cards +
                '}';
    }
}
