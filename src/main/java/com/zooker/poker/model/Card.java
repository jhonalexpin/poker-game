package com.zooker.poker.model;

import com.zooker.poker.enums.Rank;
import com.zooker.poker.enums.Suit;

import java.util.Objects;

public class Card implements Comparable{

    private Rank valueCard;
    private Suit kindCard;

    public Rank getValueCard() {
        return valueCard;
    }

    public void setValueCard(Rank valueCard) {
        this.valueCard = valueCard;
    }

    public Suit getKindCard() {
        return kindCard;
    }

    public void setKindCard(Suit kindCard) {
        this.kindCard = kindCard;
    }

    @Override
    public String toString() {
        return "Card{" +
                "valueCard=" + valueCard +
                ", kindCard=" + kindCard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return valueCard == card.valueCard && kindCard == card.kindCard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueCard, kindCard);
    }

    @Override
    public int compareTo(Object o) {
        Card card = (Card) o;
        return this.getValueCard().compareTo(card.getValueCard());
    }
}
