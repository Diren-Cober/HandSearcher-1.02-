package com.company.combination;

import com.company.card.Card;
import com.company.card.Rank;
import com.company.card.Suit;
import java.util.*;
import java.util.logging.Logger;

public class Classifier {

    private static  Logger log = Logger.getAnonymousLogger();

    private HandRanks handRanks;
    private HandSuits handSuits;
    private SortedSet<Card> cards;
    private Combination result;

    public void setCards(SortedSet<Card> inputCards){
        this.cards = inputCards;
    }

    public Classifier (){
        this.cards = new TreeSet<>();
    }

    public Combination classify (SortedSet<Card> inputCards){
        this.cards = inputCards;

        //создаём массив рангов и мастей карт для удоьства
        this.handRanks = new HandRanks(cards);
        this.handSuits = new HandSuits(cards);

        //поиск комбинации - опять же отдельно
        result = findCombination();
        return result;
    }

    private Combination findCombination (){
        //по количеству карт сокращаем путь
        //иначе - каждая "find" функция будет вызывать последующую
        if (cards.size() > 4)
            return findRoyalFlush();
        else if (cards.size() == 4)
            return findFourOfAKind();
        else if (cards.size() == 3)
            return findSet();
        else if (cards.size() == 2)
            return findPair();
        else if (cards.size() == 1)
            return findHighCard();
        //создаём "нулевую" комбинацию
        else return new Combination();


    }

    private Combination findRoyalFlush(){
        if(cards.containsAll(HandUtils.ROYAL_FLUSH_DIAMONDS)){
            //retainAll  удаляет всё кроме (...)
            cards.retainAll(HandUtils.ROYAL_FLUSH_DIAMONDS);
            //возвращаем новую комбинацию вместе с названием и картами,
            // в неё входящими
            return new Combination(HandCombination.ROYAL_FLUSH, new TreeSet<>(HandUtils.ROYAL_FLUSH_DIAMONDS));
        }
        if(cards.containsAll(HandUtils.ROYAL_FLUSH_CLUBS)){
            cards.retainAll(HandUtils.ROYAL_FLUSH_CLUBS);
            return new Combination(HandCombination.ROYAL_FLUSH, new TreeSet<>(HandUtils.ROYAL_FLUSH_CLUBS));
        }
        if(cards.containsAll(HandUtils.ROYAL_FLUSH_HEARTS)){
            cards.retainAll(HandUtils.ROYAL_FLUSH_HEARTS);
            return new Combination(HandCombination.ROYAL_FLUSH, new TreeSet<>(HandUtils.ROYAL_FLUSH_HEARTS));
        }
        if(cards.containsAll(HandUtils.ROYAL_FLUSH_SPADES)){
            cards.retainAll(HandUtils.ROYAL_FLUSH_SPADES);
            return new Combination(HandCombination.ROYAL_FLUSH, new TreeSet<>(HandUtils.ROYAL_FLUSH_SPADES));
        }
        return findStraightFlush();
    }

    private Combination findStraightFlush(){
        int counter = 0;
        //"Map<>" просто так нельзя обойти при помощи for()
        // поэтому пишем .entrySet()
        for (Map.Entry<Suit, List<Card>> i: handSuits.getSuit_cards_map().entrySet()){
            //если поле "значение" (второе поле - List<...>) содержит 5 эл-тов
            // (т.е. мы имеем 5 карт одной масти),
            //то считаем, сколько из этих эл-тов идут друг за другом
            if(i.getValue().size() == 5){
                for (int j=0; j< ( i.getValue().size() - 1 ); j++){
                    if(i.getValue().get(j).getRank() ==
                       i.getValue().get(j+1).getRank())
                        counter++;
                }
                if(counter == 5) {
                    //List<Card> i мы не можем положить в SortedSet<Card>
                    //=>делаем костыль
                    SortedSet<Card> set = new TreeSet<>(i.getValue());
                    return new Combination(HandCombination.STRAIGHT_FLUSH, set);
                }
            }
        }
        return findStraightFlushWheel();
    }

    private Combination findStraightFlushWheel() {
        if(cards.containsAll(HandUtils.STRAIGHT_FLUSH_DIAMONDS_WHEEL)){
            cards.retainAll(HandUtils.STRAIGHT_FLUSH_DIAMONDS_WHEEL);
            return new Combination(HandCombination.STRAIGHT_FLUSH_WHEEL,  new TreeSet<>(HandUtils.STRAIGHT_FLUSH_DIAMONDS_WHEEL));
        }
        if(cards.containsAll(HandUtils.STRAIGHT_FLUSH_CLUBS_WHEEL)){
            cards.retainAll(HandUtils.STRAIGHT_FLUSH_CLUBS_WHEEL);
            return new Combination(HandCombination.STRAIGHT_FLUSH_WHEEL,  new TreeSet<>(HandUtils.STRAIGHT_FLUSH_CLUBS_WHEEL));
        }
        if(cards.containsAll(HandUtils.STRAIGHT_FLUSH_HEARTS_WHEEL)){
            cards.retainAll(HandUtils.STRAIGHT_FLUSH_HEARTS_WHEEL);
            return new Combination(HandCombination.STRAIGHT_FLUSH_WHEEL,  new TreeSet<>(HandUtils.STRAIGHT_FLUSH_HEARTS_WHEEL));
        }
        if(cards.containsAll(HandUtils.STRAIGHT_FLUSH_SPADES_WHEEL)){
            cards.retainAll(HandUtils.STRAIGHT_FLUSH_SPADES_WHEEL);
            return new Combination(HandCombination.STRAIGHT_FLUSH_WHEEL,  new TreeSet<>(HandUtils.STRAIGHT_FLUSH_SPADES_WHEEL));
        }
        return findFourOfAKind();
    }

    private Combination findFourOfAKind(){
        if (this.handRanks.getFour_counter() == 1)
            return new Combination(HandCombination.FOUR_OF_A_KIND, this.handRanks.getFourList());
        else
            return findFullHouse();
    }

    private Combination findFullHouse(){
        if (this.handRanks.getPair_counter() >= 1 && this.handRanks.getSet_counter() == 1){
            SortedSet<Card> fullHouseList =
                    new TreeSet<>(this.handRanks.getPairList());
            fullHouseList.addAll(this.handRanks.getSetList());
            return new Combination(HandCombination.FULL_HOUSE, fullHouseList);
        }else
        if (this.handRanks.getSet_counter() == 2){
            return new Combination(HandCombination.FULL_HOUSE, this.handRanks.getSetList());
        }else return findFlush();
    }

    private Combination findFlush(){
        for (final Map.Entry<Suit, List<Card>> i : handSuits.getSuit_cards_map().entrySet()) {
            if (i.getValue().size() == 5) {
                return new Combination(HandCombination.FLUSH, new TreeSet<>(i.getValue()));
            }
        }
        return findStraight();
    }

    private Combination findStraight(){
        Set<Rank> rankSet = this.handRanks.getRank_cards_map().keySet();

        if(rankSet.containsAll(HandUtils.STRAIGHT_ACE))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_ACE));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_KING))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_KING));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_QUEEN))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_QUEEN));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_JACK))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_JACK));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_TEN))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_TEN));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_NINE))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_NINE));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_EIGHT))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_EIGHT));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_SEVEN))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_SEVEN));
        else if(rankSet.containsAll(HandUtils.STRAIGHT_SIX))
            return new Combination(HandCombination.STRAIGHT, findStraightCards(HandUtils.STRAIGHT_SIX));

        return findWheel();
    }

    private Combination findWheel(){
        List<Rank> ranks = new ArrayList<>();
        //получаем полный список всех встречающихся в колоде рангов
        for (Map.Entry<Rank, List<Card>> i: handRanks.getRank_cards_map().entrySet()){
            ranks.add(i.getKey());
        }

        //если набирается колесо
        if(ranks.containsAll(HandUtils.WHEEL)) {
            SortedSet<Card> result = new TreeSet<>();
            //то ищем среди карт руки те, которые могут входить в комбинацию,
            // затем берём первую попавшуюся
            for (Rank i: HandUtils.WHEEL)
                result.add(
                        handRanks.getRank_cards_map()
                                .get(i)
                                .get(0)
                );

            return new Combination(HandCombination.WHEEL, result);
        }

        return findSet();
    }

    private SortedSet<Card> findStraightCards(List<Rank> ranks){
        SortedSet<Card> result = new TreeSet<>();
        for (Rank i: ranks){
            List<Card> cards = this.handRanks.getRank_cards_map().get(i);
            //среди карт одинакового ранга, входящих в straight,
            //берётся первая попавшаяся
            result.add(cards.get(0));
        }
        return result;
    }

    private Combination findSet(){
        if (this.handRanks.getSet_counter() == 1)
            return new Combination(HandCombination.SET, this.handRanks.getSetList());
        return findTwoPair();
    }

    private Combination findTwoPair(){
        if (this.handRanks.getPair_counter() == 2)
            return new Combination(HandCombination.TWO_PAIR, this.handRanks.getPairList());
        return findPair();
    }

    private Combination findPair(){
        if (this.handRanks.getPair_counter() == 1)
            return new Combination(HandCombination.PAIR, this.handRanks.getPairList());
        return findHighCard();
    }

    private Combination findHighCard(){
        Rank maxRank = Rank.TWO;
        Card result_card = new Card(maxRank, Suit.CLUBS);
        for(Map.Entry<Rank, List<Card>> i: this.handRanks.getRank_cards_map().entrySet()){
            if (i.getKey().getRank_value() > maxRank.getRank_value()){
                maxRank = i.getKey();
                result_card = i.getValue().get(0);
            }
        }
        SortedSet<Card> result = new TreeSet<>();
        result.add(result_card);
        return new Combination(HandCombination.HIGH_CARD, result );
    }

}
