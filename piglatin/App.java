package piglatin;

public class App {
    public static void main(String[] args) {

        // int score = TestSuite.run();
        System.out.println("hi");

        if (true) {
            Book input = new Book();

            input.readFromUrl("Romeo and Juliette",
                    "https://gutenberg.pglaf.org/cache/epub/1513/pg1513.txt");

            input.printlines(0, 2);
            Book output = PigLatinTranslator.translate(input);
            output.printlines(0, 2);
            output.writeToFile("output/omeoray.txt");
            input.writeToFile("output/original.txt");
        }
    }
}
