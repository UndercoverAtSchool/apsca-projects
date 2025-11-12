package cards;

public class Game {

    private Deck deck;
    private Hand player;
    private Hand dealer;

    private int playersWins;
    private int dealersWins;

    public Game(Deck deck)
    {
        this.deck = deck;
        playersWins = 0;
        dealersWins = 0;
    }

    public void printRules()
    {
        System.out.println(" --- Rules / How to play --- ");
        System.out.println("s to stand, h to hit");
    }

    private void printState()
    {
        System.out.println("Player - Score: " + score(player) + " Hand: " + player.toString());
        System.out.println("Dealer - Score: " + score(dealer) + " Hand: " + dealer.toString());
    }

    public void next()
    {
        player = new Hand(13);
        dealer = new Hand(13);
        deal();
    }

    public void deal()
    {
        Card card;
        card = deck.draw();
        player.add(card);

        card = deck.draw();
        dealer.add(card);

        card = deck.draw();
        player.add(card);

        card = deck.draw();
        dealer.add(card);

        System.out.println("Player - Score: " + score(player) + " Hand: " + player.toString());
        System.out.println("Dealer - Score: " + score(dealer) + " Hand: " + dealer.toString());

    }

    public boolean takeTurn(String action)
    {
        Boolean standing = false;
        if (action.length() > 0)
        {
            // Player's Turn
            String command = action.substring(0, 1);
            if (command.equals("h"))
            {
                System.out.println("Hit");
                Card card = deck.draw();
                player.add(card);
            }
            else if (command.equals("s"))
            {
                System.out.println("Stand");
                standing = true;
            }

            // Evaluate Player's Score
            int playerScore = score(player);
            if (playerScore == 21)
            {
                System.out.println("You win!");
                playersWins++;
                printState();
                return false;
            }
            if (playerScore > 21)
            {
                System.out.println("BUST! You loose.");
                dealersWins++;
                printState();
                return false;
            }

            // Dealer's Turn
            if (score(dealer) < 17)
            {
                Card card = deck.draw();
                dealer.add(card);
            }

            // Evaluate Dealer's Score
            int dealerScore = score(dealer);
            if (dealerScore == 21)
            {
                System.out.println("Dealer wins.");
                dealersWins++;
                printState();
                return false;
            }
            if (dealerScore > 21)
            {
                System.out.println("You win! Dealer bust.");
                playersWins++;
                printState();
                return false;
            }

            if ((score(dealer) >= 17) && standing)
            {
                System.out.println("Game done.");
                if (score(player) > score(dealer))
                {
                    playersWins++;
                    System.out.println("Player wins!");
                }
                else {
                    dealersWins++;
                    System.out.println("Player wins!");
                }
                printState();
                return false;
            }

            printState();
            return true;
        }
        return true;
    }

    private int score(Hand hand)
    {
        int score = 0;
        for (int i=0; i<hand.length(); i++)
        {
            Card card = hand.get(i);
            int value = card.getValue() + 1;
            if (value > 10)
            {
                value = 10;
            }
            // TODO: deal with aces
            score += value;
        }
        return score;
    }

}
