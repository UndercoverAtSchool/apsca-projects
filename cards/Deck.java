package cards;

import java.util.*;
import java.util.stream.IntStream;

public class Deck {
    private Card[] cards;
    private int drawIndex;

    public Deck() {
        this.cards = IntStream.range(0, 52)
                .mapToObj(i -> new Card(i % 4, i / 4))
                .toArray(Card[]::new);
        drawIndex = 0;
    }

    public void shuffle() {
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        this.cards = cardList.toArray(Card[]::new);
        drawIndex = 0;
    }

    public Card draw() {
        if (drawIndex >= 52)
            return null;
        return cards[drawIndex++];
    }

    public void print(int val) {
        for (int i = 0; i < val; i++) {
            System.out.print(cards[i] + " ");
        }
        System.out.println();
    }

    public void cut(int at) {
        if (at <= 0 || at >= cards.length)
            return;
        Card[] newCards = new Card[cards.length];
        System.arraycopy(cards, at, newCards, 0, cards.length - at);
        System.arraycopy(cards, 0, newCards, cards.length - at, at);
        cards = newCards;
        drawIndex = 0;
    }

}
