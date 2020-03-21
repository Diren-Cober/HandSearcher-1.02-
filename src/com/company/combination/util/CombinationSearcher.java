package com.company.combination.util;

import com.company.card.Card;
import com.company.combination.Combination;
import java.util.SortedSet;

public interface CombinationSearcher {
    Combination analyzeCards(SortedSet<Card> inputCards);
    Combination analyzeIntegers(SortedSet<Integer> inputCards);
}
