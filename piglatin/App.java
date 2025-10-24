package piglatin;

public class App {
    public static void main(String[] args) {

        // int score = TestSuite.run();

        if (true) {
            Book input = new Book();

            final String bookTitle = "Romeo and Juliette"; // La figlia di Jefte
            final String fileName = "omeoray"; // igliafay
            final int bookId = 1513; // 1513, 77118
            // https://www.gutenberg.org/cache/epub/77118/pg77118.txt

            input.readFromUrl(bookTitle,
                    String.format("https://gutenberg.pglaf.org/cache/epub/%1$d/pg%1$d.txt", bookId));

            input.writeToFile("output/original.txt");

            Book output = PigLatinTranslator.translate(input);

            output.writeToFile(String.format("output/%s.txt", fileName));
        }
    }
}
