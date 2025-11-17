package cards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    private int maxCards;
    private List<Card> cards = new ArrayList<Card>();

    public Hand(int maxCards) {
        this.maxCards = maxCards;
    }

    public void add(Card card) {
        if (cards.size() == maxCards)
            return;
        cards.add(card);
    }

    public int length() {
        return cards.size();
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public Card remove(int index) {
        return cards.remove(index);
    }

    public String toString() {
        return cards.stream()
                .map(c -> c.toString())
                .collect(Collectors.joining(" "));
    }
}
