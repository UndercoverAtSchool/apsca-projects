package cards;

import java.util.Scanner;

public class CardTable {

    Deck deck;

    public CardTable()
    {
        deck = new Deck();
    }

    public void startPlaying()
    {
        deck.shuffle();

        Scanner keyboard = new Scanner(System.in);

        Game game = new Game(deck);

        game.printRules();
        game.next();

        String input = " ";
        Boolean play = true;
        while (play)
        {
            // Get input
            System.out.println("Enter a command: (q to quit)");
            input = keyboard.nextLine();

            // Check for exit condition
            if (input.length() > 0)
            {
                String command = input.substring(0, 1);
                if (command.equals("q"))
                {
                    play = false;
                    continue;
                }
                else if (command.equals("r")) {
                    // Reset
                    game = new Game(deck);
                }
                else {
                    // Play on!
                    boolean result = game.takeTurn(command);
                    if (result == false)
                    {
                        System.out.println("\n");
                        game.next();
                    }
                }
            }
        }
        keyboard.close();
   }
}
