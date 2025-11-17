package cards;

public class Card {
    private int rank;
    private int suit; // diamond, clubs, hearts, spades

    public Card(int suit, int rank) {
        this.suit = Math.clamp(rank, 0, 3);
        this.rank = Math.clamp(rank, 0, 12);
    }

    public int getValue() {
        return rank;
    }

    public String toString() {
        String suits = "♦♣♥♠";
        String[] ranks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
        return suits.charAt(suit) + ranks[rank];
    }

}
