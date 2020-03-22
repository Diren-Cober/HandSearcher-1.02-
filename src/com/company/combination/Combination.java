package com.company.combination;

import com.company.card.Card;
import com.company.card.Rank;   //Не используется.

import java.util.SortedSet;

public class Combination {
    private HandCombination combinationRank;
    private SortedSet<Card> combination_cards;

    //комбинация - цель поиска всего анализатора
    public Combination (HandCombination r, SortedSet<Card> cards){
        this.combinationRank = r;
        this.combination_cards = cards;
    }

    //если карт нет, создаётся это
    public Combination(){
        this.combinationRank = HandCombination.NO_CARDS;
        this.combination_cards = null;
    }

    public HandCombination getCombinationRank() {
        return combinationRank;
    }

    public SortedSet<Card> getCombination_cards() {
        return combination_cards;
    }
}
