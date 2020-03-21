package com.company.combination;

import com.company.card.Card;
import com.company.card.Suit;
import java.util.*;
import java.util.stream.Collectors;

public class HandSuits{

    private Map<Suit, List<Card>> suit_cards_map;

    //см HandRanks
    HandSuits(SortedSet<Card> cards){
        this.suit_cards_map = sortSuits(cards);
    }

    public Map<Suit, List<Card>> sortSuits(SortedSet<Card> cards){
        //тут сортировка не нужна (по мастям не сравниваем)
        Map result = new HashMap(
                    cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit))
                    );

        return result;
    }

    public Map<Suit, List<Card>> getSuit_cards_map() {
        return suit_cards_map;
    }

}

