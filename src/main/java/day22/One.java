package day22;

import utils.Pair;
import utils.sources.StringFromFileSupplier;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.*;

public class One
{
    enum Action
    {
        CUT, REVERSE, DEAL;
    }
    
    static class Card
    {
        long id;
        
        public Card(long id)
        {
            this.id = id;
        }
        
        @Override
        public String toString()
        {
            return "Card{" +
                   "id=" + id +
                   '}';
        }
    }
    
    public static void main(String[] args)
    {
        List<String>                input = StringFromFileSupplier.create("day22.input", false).getDataSource();
        List<Pair<Action, Integer>> moves = generateMoveList(input);
        AtomicReference<List<Card>> deck  = new AtomicReference<>(IntStream.range(0, 10007).mapToObj(Card::new).collect(Collectors.toList()));
        doMoves(moves, deck);
        findCard(deck, 2019);
    }
    
    public static void findCard(AtomicReference<List<Card>> deck, long cardValue)
    {
        List<Card> get = deck.get();
        for (int i = 0; i < get.size(); i++)
        {
            Card card = get.get(i);
            if (card.id == cardValue)
            {
                System.out.println(i);
            }
        }
    }
    
    public static void doMoves(List<Pair<Action, Integer>> moves, AtomicReference<List<Card>> deck)
    {
        moves.forEach(p -> {
            switch (p.getA())
            {
                case CUT:
                    deck.set(cut(deck.get(), p.getB()));
                    break;
                case REVERSE:
                    deck.set(dealNewStack(deck.get()));
                    break;
                case DEAL:
                    deck.set(dealWithIncrement(deck.get(), p.getB()));
                    break;
            }
        });
    }
    
    private static List<Card> dealWithIncrement(List<Card> cards, int inc)
    {
        List<Card> newCards = new ArrayList<>(cards);
        for (int i = 0; i < cards.size(); i++)
        {
            int index = (i * inc) % cards.size();
            newCards.set(index, cards.get(i));
        }
        return newCards;
    }
    
    private static List<Card> cut(List<Card> cards, int cut)
    {
        if (cut > 0)
        {
            return cutFromTop(cards, cut);
        }
        
        return cutFromBottom(cards, cut);
    }
    
    private static List<Card> cutFromBottom(List<Card> cards, int cut)
    {
        List<Card> top    = cards.stream().limit(cards.size() + cut).collect(Collectors.toList());
        List<Card> bottom = cards.stream().skip(cards.size() + cut).collect(Collectors.toList());
        
        bottom.addAll(top);
        return bottom;
    }
    
    private static List<Card> cutFromTop(List<Card> cards, int cut)
    {
        List<Card> top    = cards.stream().limit(cut).collect(Collectors.toList());
        List<Card> bottom = cards.stream().skip(cut).collect(Collectors.toList());
        
        bottom.addAll(top);
        return bottom;
    }
    
    private static List<Card> dealNewStack(List<Card> cards)
    {
        List<Card> newStack = new ArrayList<>(cards);
        Collections.reverse(newStack);
        return newStack;
    }
    
    public static List<Pair<Action, Integer>> generateMoveList(List<String> input)
    {
        return input.stream().map(a -> {
            if (a.startsWith("cut"))
            {
                return new Pair<>(Action.CUT, Integer.parseInt(a.split(" ")[1]));
            }
            
            if (a.startsWith("deal into"))
            {
                return new Pair<>(Action.REVERSE, 1);
            }
            
            if (a.startsWith("deal with"))
            {
                return new Pair<>(Action.DEAL, Integer.parseInt(a.split(" ")[3]));
            }
            
            return null;
        }).collect(Collectors.toList());
    }
}
