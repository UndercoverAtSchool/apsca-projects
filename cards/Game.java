package cards;

public class Game {

    private Deck deck;
    private Hand player;
    private Hand dealer;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    @SuppressWarnings("unused")
    private int playersWins;
    @SuppressWarnings("unused")
    private int dealersWins;

    public Game(Deck deck) {
        this.deck = deck;
        playersWins = 0;
        dealersWins = 0;
    }

    public void printRules() {
        System.out.println(" --- Rules / How to play --- ");
        System.out.println("s to stand, h to hit");
    }

    private void printState() {
        String s1 = "│ Player - Score: " + score(player) + " Hand: " + player.toString() + " │";
        String s2 = "│ Dealer - Score: " + score(dealer) + " Hand: " + dealer.toString() + " │";
        int maxLen = Math.max(s1.length(), s2.length());
        String topBorder = "┌" + "─".repeat(maxLen - 2) + "┐";
        String bottomBorder = "└" + "─".repeat(maxLen - 2) + "┘";
        String paddedS1 = s1.substring(0, s1.length() - 1) + " ".repeat(maxLen - s1.length()) + "│";
        String paddedS2 = s2.substring(0, s2.length() - 1) + " ".repeat(maxLen - s2.length()) + "│";
        System.out.println(topBorder);
        System.out.println(paddedS1);
        System.out.println(paddedS2);
        System.out.println(bottomBorder);
    }

    public void next() {
        player = new Hand(13);
        dealer = new Hand(13);
        deal();
    }

    public void deal() {
        Card card;
        card = deck.draw();
        player.add(card);

        card = deck.draw();
        dealer.add(card);

        card = deck.draw();
        player.add(card);

        card = deck.draw();
        dealer.add(card);

        printState();
    }

    public boolean takeTurn(String action) {
        Boolean standing = false;
        if (action.length() > 0) {
            // Player's Turn
            String command = action.substring(0, 1);
            if (command.equals("h")) {
                System.out.println("Hit");
                Card card = deck.draw();
                player.add(card);
            } else if (command.equals("s")) {
                System.out.println("Stand");
                standing = true;
            }

            // Evaluate Player's Score
            int playerScore = score(player);
            if (playerScore == 21) {
                printlnColor("YOU WIN!", ANSI_GREEN);
                playersWins++;
                printState();
                System.out.println("-".repeat(30));
                return false;
            }
            if (playerScore > 21) {
                printlnColor("BUST! You loose.", ANSI_RED);
                dealersWins++;
                printState();
                System.out.println("-".repeat(30));
                return false;
            }

            // Dealer's Turn
            if (score(dealer) < 17) {
                Card card = deck.draw();
                dealer.add(card);
            }

            // Evaluate Dealer's Score
            int dealerScore = score(dealer);
            if (dealerScore == 21) {
                printlnColor("Dealer wins (blackjack!).", ANSI_RED);
                dealersWins++;
                printState();
                System.out.println("-".repeat(30));
                return false;
            }
            if (dealerScore > 21) {
                printlnColor("You win! Dealer bust.", ANSI_GREEN);
                playersWins++;
                printState();
                System.out.println("-".repeat(30));
                return false;
            }

            if ((score(dealer) >= 17) && standing) {
                if (score(player) > score(dealer)) {
                    playersWins++;
                    printlnColor("Player wins!", ANSI_GREEN);
                } else {
                    dealersWins++;
                    printlnColor("Player wins!", ANSI_RED);
                }
                printState();
                System.out.println("-".repeat(30));
                return false;
            }

            printState();
            return true;
        }
        return true;
    }

    private void printlnColor(String text, String color) {
        System.out.println(color + text + ANSI_RESET);
    }

    private int score(Hand hand) {
        int score = 0;
        boolean hasAce = false;
        for (int i = 0; i < hand.length(); i++) {
            Card card = hand.get(i);
            int value = card.getValue() + 1;
            if (value > 10) {
                value = 10;
            }
            // TODO: deal with aces
            score += value;
            if (value == 1)
                hasAce = true;
        }
        if (score <= 11 && hasAce) {
            score += 10;
        }
        return score;
    }

}
