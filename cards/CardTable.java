package cards;

import java.util.Scanner;

public class CardTable {

    Deck deck;

    public CardTable() {
        deck = new Deck();
    }

    public void startPlaying() {
        deck.shuffle();

        Scanner keyboard = new Scanner(System.in);

        Game game = new Game(deck);

        game.printRules();
        game.next();

        String input = " ";
        Boolean play = true;
        while (play) {
            // Get input
            System.out.println("Enter a command: (q to quit)");
            input = keyboard.nextLine();

            // Check for exit condition
            if (input.length() > 0) {
                String command = input.substring(0, 1);
                if (command.equals("q")) {
                    play = false;
                    continue;
                } else if (command.equals("r")) {
                    // Reset
                    deck = new Deck();
                    deck.shuffle();
                    game = new Game(deck);
                    game.next();
                } else if (command.equals("h") || command.equals("s")) {
                    // Play on!
                    if (!game.takeTurn(command)) {
                        System.out.println("\n");
                        deck = new Deck();
                        deck.shuffle();
                        game = new Game(deck);
                        game.next();
                    }
                }
            }
        }
        keyboard.close();
    }
}
