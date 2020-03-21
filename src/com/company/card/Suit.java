package com.company.card;

public enum Suit {
    DIAMONDS(1),
    CLUBS(2),
    HEARTS(3),
    SPADES(4);

    private int suit_value;

    Suit(int value) {
        this.suit_value = value;
    }

    public int getSuit_value() {
        return this.suit_value;
    }
}
