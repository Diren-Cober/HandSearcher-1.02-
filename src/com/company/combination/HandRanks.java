package com.company.combination;

import com.company.card.Card;
import com.company.card.Rank;
import java.util.*;
import java.util.stream.Collectors;

public class HandRanks{

    private Map<Rank, List<Card>> rank_cards_map;
    private int four_counter;
    private int set_counter;
    private int pair_counter;

    //класс рангов всех карт в руке
    public HandRanks (SortedSet<Card> cards){
        this.rank_cards_map = sortRanks(cards);
        //количество пар/троек/четвёрок
        this.four_counter = numberOfGroups(4);
        this.set_counter = numberOfGroups(3);
        this.pair_counter = numberOfGroups(2);

    }

    //get...List возвращает все карты соответствующей
    //комбинации
    public SortedSet<Card> getFourList(){
        SortedSet<Card> result = new TreeSet<>();
        for (Map.Entry<Rank, List<Card>> i: rank_cards_map.entrySet()){
            if(i.getValue().size() == 4)
                result.addAll(i.getValue());
        }
        return result;
    }

    public SortedSet<Card> getSetList(){
        SortedSet<Card> result = new TreeSet<>();
        for (Map.Entry<Rank, List<Card>> i: rank_cards_map.entrySet()){
            if(i.getValue().size() == 3)
                result.addAll(i.getValue());
        }
        return result;
    }

    public SortedSet<Card> getPairList(){
        SortedSet<Card> result = new TreeSet<>();
        for (Map.Entry<Rank, List<Card>> i: rank_cards_map.entrySet()){
            if(i.getValue().size() == 2)
                result.addAll(i.getValue());
        }
        return result;
    }

    //считает количество пар/троек/четвёрок карт
    public int numberOfGroups(int size){
        int counter = 0;
        for (Map.Entry<Rank, List<Card>> i: rank_cards_map.entrySet()){
            if(i.getValue().size() == size)
                counter++;
        }
        return counter;
    }


    //сортирует массив карт, привязывает карты к их рангам
    private Map<Rank, List<Card>> sortRanks(SortedSet<Card> cards) {


        //ранги сортируются по количеству карт (если карт одинаково, то сравниваются сами ранги
        Comparator<Map.Entry<Rank, List<Card>>> cardComparator = new Comparator<Map.Entry<Rank, List<Card>>>() {
            @Override
            public int compare(Map.Entry<Rank, List<Card>> o1, Map.Entry<Rank, List<Card>> o2) {
                //количество >= ? разность количества : разность рангов
                if (o1.getValue().size()==o2.getValue().size())
                    return o2.getKey().getRank_value() - o1.getKey().getRank_value();
                else
                    return o2.getValue().size() - o1.getValue().size();
            }
        };

        //создаётся массив (List) из руки карт; к карте привязывается её ранг
        //Map.Entry - как бы одна связка в мапе
        //лист нужен для аортировки
        List<Map.Entry<Rank, List<Card>>> cards_list =
                new ArrayList<>(
                        cards
                                //перевести лист в поток данных ( в простой набор значений)
                                .stream()
                                //привязка рангов к картам
                                .collect(
                                Collectors.groupingBy(Card::getRank)
                        ).entrySet() //сделать итерируемой (можно ставить в for (... : ...) - forEach)
                );

        cards_list.sort(cardComparator);


        Map<Rank, List<Card>> result = new HashMap<>();

        //переводим лист в мапу
        for(Map.Entry<Rank, List<Card>> map: cards_list)
            result.put(map.getKey(), map.getValue());

        return result;
    }

    public Map<Rank, List<Card>> getRank_cards_map(){
        return this.rank_cards_map;
    }

    public int getFour_counter() {
        return four_counter;
    }

    public int getSet_counter() {
        return set_counter;
    }

    public int getPair_counter() {
        return pair_counter;
    }

}

