package piglatin;

import java.util.stream.IntStream;

public class PigLatinTranslator {
    public static Book translate(Book input) {
        Book translatedBook = new Book();
        translatedBook.setTitle(input.getTitle() + " Translated");

        for (int i = 0; i < input.getLineCount(); i++) {
            String line = input.getLine(i);
            translatedBook.appendLine(translate(line));
        }

        return translatedBook;
    }

    public static boolean contains(final char[] arr, final char key) {
        for (char c : arr) {
            if (c == key) {
                return true;
            }
        }
        return false;
    }

    public static boolean wordValid(Character c) {
        return Character.isLetter(c) || c == '-';
    }

    public static String translate(String input) {
        // System.out.println(" -> translate('" + input + "')");

        String result = "";

        int pointer = 0;
        String word = "";
        input += " ";
        while (pointer < input.length()) {
            Character c = input.charAt(pointer);
            pointer++;
            if (wordValid(c)) {
                word += c;
            } else {
                result += translateWord(word) + c.toString();
                word = "";
                continue;
            }

        }

        return result.substring(0, result.length() - 1);
    }

    private static String translateWord(String input) {
        // System.out.println(" -> translateWord('" + input + "')");

        if (input.length() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        int pointer = 0;
        final String tempin = input;
        int[] capitals = IntStream.range(0, tempin.length())
                .map(n -> Character.isUpperCase(tempin.charAt(n)) ? 1 : 0)
                .toArray();
        input = input.toLowerCase();

        while (pointer < input.length() && "aeiouy".indexOf(input.charAt(pointer)) == -1) {
            Character c = input.charAt(pointer);
            result.append(c.toString());
            pointer++;
        }

        result = new StringBuilder(input.substring(pointer) + result.toString());

        for (int i = 0; i < capitals.length; i++) {
            int capital = capitals[i];
            if (capital == 1) {
                result.setCharAt(i, Character.toUpperCase(result.charAt(i)));
            }
        }

        int capitalCount = IntStream.of(capitals).sum();

        return result.toString() + (capitalCount == input.length() ? "AY" : "ay");
    }

}
