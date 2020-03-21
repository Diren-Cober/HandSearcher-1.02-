package com.company.comparator;

import com.company.card.Card;
import com.company.card.Rank;
import com.company.card.Suit;
import com.company.card.UCException;
import com.company.combination.Combination;
import com.company.combination.HandSearcher;
import javax.swing.plaf.basic.BasicTreeUI;
import java.util.*;

public class HandComparator {

    private HandSearcher handSearcher;

    private Combination comb1, comb2;
    private SortedSet<Card> allCards1, allCards2;

    public HandComparator(){ this.handSearcher = new HandSearcher(); }


    //возвращает 1, когда первая рука сильнее, 2, когда вторая
    //и 0, если они полностью совпалают,
    // -1 - код ошибки
    public int compare(ArrayList<Integer> cards1, ArrayList<Integer> cards2){
        //получаем комбинации для обоих противников
        this.comb1 = handSearcher.analyzeIntegers(cards1);
        this.comb2 = handSearcher.analyzeIntegers(cards2);

        //на случай кикера откладываем полные колоды
        this.allCards1 = convertIntsToCards(cards1);
        this.allCards2 = convertIntsToCards(cards2);

        int noKicker = compareWithoutKicker();
        if (noKicker != 0)
            //если можно сравнить без кикера
            return noKicker;
        else
            //сравнение с кикером
            return kickerCompare();

    }

    private int kickerCompare(){
        //убираем все карты, участвующие в комбинации
        allCards1.removeAll(comb1.getCombination_cards());
        allCards2.removeAll(comb2.getCombination_cards());

        List<Integer> rankVals1 = new LinkedList<>();
        List<Integer> rankVals2 = new LinkedList<>();

        //получаем листы рангов
        for(Card i: allCards1)
            rankVals1.add(i.getRank().getRank_value());
        for(Card i: allCards2)
            rankVals2.add(i.getRank().getRank_value());

        int k=0;
        int n=rankVals1.size()-1; // ==rankVals2-1
        while (k < n){
            //сравниваем сначала последние (самые большие, т.к. allCards - SortedSet)
            if(rankVals1.get(n-k) >rankVals2.get(n-k))
                return 1;
            else
            if(rankVals1.get(n-k) < rankVals2.get(n-k))
                return 2;
            k++;
            //иначе переходим к картам ниже рангом
        }

        return 0;
    }

    private int compareWithoutKicker(){
        if(comb1.getCombinationRank() != comb2.getCombinationRank())
            return comb1.getCombinationRank().getValue() > comb2.getCombinationRank().getValue() ? 1:2;
        else
            switch (comb1.getCombinationRank()){
                /*case HIGH_CARD:
                case PAIR:
                case TWO_PAIR:
                case SET:
                case WHEEL:
                case STRAIGHT:
                case FLUSH:
                case FULL_HOUSE:
                case FOUR_OF_A_KIND:
                case STRAIGHT_FLUSH: return compareCombinations();*/

                //невозможно сравнить
                case STRAIGHT_FLUSH_WHEEL:
                case ROYAL_FLUSH: return 0;

                //ошибка
                case NO_CARDS: return -1;

                //сравниваем
                default: return compareCombinations(comb1, comb2);
            }
    }

    //сравнивает две комбинации по рангам карт в них
    private int compareCombinations(Combination combination1, Combination combination2){

        List<Integer> rankVals1 = new LinkedList<>();
        List<Integer> rankVals2 = new LinkedList<>();

        //получаем набор рангов по комбинации
        for(Card i: combination1.getCombination_cards())
            rankVals1.add(i.getRank().getRank_value());
        for(Card i: combination2.getCombination_cards())
            rankVals2.add(i.getRank().getRank_value());

        int k=0;
        int n=rankVals1.size()-1; // ==rankVals2-1
        while (k < n){
            //сравниваем сначала последние (самые большие, т.к. allCards - SortedSet)
            if(rankVals1.get(n-k) >rankVals2.get(n-k))
                return 1;
            else
                if(rankVals1.get(n-k) < rankVals2.get(n-k))
                    return 2;
            k++;
            //иначе переходим к картам ниже рангом
        }
        return 0;
    }

    //получаем карты из кодов
    private SortedSet<Card> convertIntsToCards(ArrayList<Integer> cards) {
        SortedSet<Card> result = new TreeSet<>();
        for(Integer i: cards) {
            try {
                result.add(new Card(i));
            } catch (UCException uce) {
                System.out.println(uce.getMessage() + ':' + ' ' + uce.getCode());
            }
        }
        return result;
    }
}
