package com.company.combination;

import com.company.card.Card;
import com.company.card.UCException;
import com.company.combination.util.CombinationSearcher;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Logger;

public class HandSearcher{

    private static Logger log = Logger.getAnonymousLogger();

    private Classifier classifier;

    public HandSearcher(){
        //мы можем создать одинэкземпляр класса и вызывать функцию несколько раз
        classifier = new Classifier();
    }

    public Combination analyzeCards(ArrayList<Card> inputCards){
        return classifier.classify(new TreeSet<>(inputCards));
    }

    public Combination analyzeIntegers(ArrayList<Integer> inputCards){
        ArrayList<Card> cards = new ArrayList<>();

        //переводим числовые коды карт в объекты класса Card
        for (Integer i: inputCards) {
            try {
                cards.add(new Card(i));
            } catch (UCException uce) {
                System.out.println(uce.getMessage() + ':' + ' ' + uce.getCode());
            }
        }

        return analyzeCards(cards);
    }



}
