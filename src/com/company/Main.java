package com.company;

import com.company.card.Card;
import com.company.combination.Combination;
import com.company.combination.HandSearcher;
import com.company.combination.HandUtils;
import com.company.combination.util.CombinationSearcher;
import com.company.comparator.HandComparator;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> hand1 = new ArrayList<>();
        hand1.add(310);
        hand1.add(414);
        hand1.add(312);
        hand1.add(305);
        hand1.add(410);
        hand1.add(210);
        hand1.add(112);
        ArrayList<Integer> hand2 = new ArrayList<>();
        hand2.add(410);
        hand2.add(414);
        hand2.add(413);
        hand2.add(412);
        hand2.add(411);
        hand2.add(210);
        hand2.add(112);
        ArrayList<Integer> hand3 = new ArrayList<>();
        hand3.add(102);
        hand3.add(304);/*
        hand3.add(413);
        hand3.add(412);
        hand3.add(411);
        hand3.add(210);*/
        hand3.add(112);
        ArrayList<Integer> hand4 = new ArrayList<>();
        hand4.add(310);
        hand4.add(314);
        hand4.add(313);
        hand4.add(312);
        hand4.add(311);
        hand4.add(210);
        hand4.add(112);

        HandComparator handComparator = new HandComparator();
        switch(handComparator.compare(hand2, hand4)){
            case 1: System.out.println("The first hand has a bigger rank."); break;
            case 2: System.out.println("The second hand has a bigger rank."); break;
            case 0: System.out.println("Both hands have equal ranks."); break;
            case -1:
            default:
                System.out.println("Error. Invalid values.");
        }

        testHandSearcher(hand2, hand4);

    }

    static void testHandSearcher(ArrayList<Integer> hand_1, ArrayList<Integer> hand_2){

        System.out.println("\nTwo hands searching test.\nCards in the first hand: ");
        for(Integer i: hand_1)
            System.out.println(i);
        System.out.println("Cards in the second hand: ");
        for(Integer i: hand_2)
            System.out.println(i);

        HandSearcher handSearcher = new HandSearcher();

        Combination resultCombinationIntegers = handSearcher.analyzeIntegers(hand_1);
        if (resultCombinationIntegers.getCombination_cards() != null) {
            System.out.println("Combination in the first hand: " + resultCombinationIntegers.getCombinationRank()+'\n'+"Cards in combination: ");
            for(Card i: resultCombinationIntegers.getCombination_cards())
                System.out.println(i.getCode()+": "+i.getRank()+" of "+i.getSuit());
        }else
            System.out.println("Error! No cards. Combination: "+resultCombinationIntegers.getCombinationRank().toString());


        Combination resultCombinationCards = handSearcher.analyzeIntegers(hand_2);
        if (resultCombinationCards.getCombination_cards()!=null) {
            System.out.println("Combination in the first hand: " + resultCombinationCards.getCombinationRank()+' '+resultCombinationCards.getCombination_cards().size()+'\n'+"Cards in combination: ");
            for(Card i: resultCombinationCards.getCombination_cards()) System.out.println(i.getCode()+": "+i.getRank()+" of "+i.getSuit());
        }else
            System.out.println("Error! No cards. Combination: "+resultCombinationCards.getCombinationRank().toString());
    }

    static void testHandSearcher(){
        System.out.println("Testing HandSearcher. HandSearcher finds the power of combination and the cards in it.\nCards in array with Integer codes: ");

        ArrayList<Card> myHandCards = new ArrayList<>(HandUtils.STRAIGHT_FLUSH_CLUBS_WHEEL);
        ArrayList<Integer> myHandIntegers = new ArrayList<>();
        myHandIntegers.add(304);
        myHandIntegers.add(305);
        myHandIntegers.add(303);
        myHandIntegers.add(314);
        myHandIntegers.add(302);

        for(Integer i: myHandIntegers)
            System.out.println(i);
        System.out.println("Cards in array with \"Card\" classes: ");
        for(Card i: myHandCards)
            System.out.println(i.getCode()+": "+i.getRank()+" of "+i.getSuit());

        HandSearcher handSearcher = new HandSearcher();

        Combination resultCombinationIntegers = handSearcher.analyzeIntegers(myHandIntegers);
        if (resultCombinationIntegers.getCombination_cards() != null) {
            System.out.println("Combination in \"myHandIntegers\" SortedSet: " + resultCombinationIntegers.getCombinationRank()+'\n'+"Cards in combination: ");
            for(Card i: resultCombinationIntegers.getCombination_cards())
                System.out.println(i.getCode()+": "+i.getRank()+" of "+i.getSuit());
        }else
            System.out.println("Error! No cards. Combination: "+resultCombinationIntegers.getCombinationRank().toString());


        Combination resultCombinationCards = handSearcher.analyzeCards(myHandCards);
        if (resultCombinationCards.getCombination_cards()!=null) {
            System.out.println("Combination in \"myHandCards\" SortedSet: " + resultCombinationCards.getCombinationRank()+'\n'+"Cards in combination: ");
            for(Card i: resultCombinationCards.getCombination_cards()) System.out.println(i.getCode()+": "+i.getRank()+" of "+i.getSuit());
        }else
            System.out.println("Error! No cards. Combination: "+resultCombinationCards.getCombinationRank().toString());
    }
}
