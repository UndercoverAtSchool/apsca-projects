package piglatin;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PigLatinTranslator {
    public static Book translate(Book input) {
        Book translatedBook = new Book();

        // TODO: Add code here to populate translatedBook with a translation of the
        // input book.
        // Curent do-nothing code will return an empty book.
        // Your code will need to call translate(String input) many times.

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
        System.out.println("  -> translate('" + input + "')");

        String result = "";

        // input = "." + input;
        // while (input.length() > 0) {
        // String word = "";
        // Character c = input.charAt(0);
        // while (Character.isLetter(c)) {
        // word += c.toString();
        // input = input.substring(1);
        // }
        // result += (word.length() > 0 ? translateWord(word) : "") + c.toString();
        // input = input.substring(1);
        // }

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

        // TODO: translate a string input, store in result.
        // The input to this function could be any English string.
        // It may be made up of many words.
        // This method must call translateWord once for each word in the string.
        return result.substring(0, result.length() - 1);
    }

    private static String translateWord(String input) {
        System.out.println("  -> translateWord('" + input + "')");

        if (input.length() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        // while (input.length() > 0 && !contains(new char[] { 'a', 'e', 'i', 'o', 'u'
        // }, input.charAt(0))) {
        // result += input.substring(0, 1);
        // input = input.substring(1);
        // }

        int pointer = 0;
        final String tempin = input;
        int[] capitals = IntStream.range(0, tempin.length()).map(n -> Character.isUpperCase(tempin.charAt(n)) ? 1 : 0)
                .toArray();
        input = input.toLowerCase();

        while (pointer < input.length() && !contains(new char[] { 'a', 'e', 'i', 'o', 'u' }, input.charAt(pointer))) {
            Character c = input.charAt(pointer);
            result.append(c.toString());
            pointer++;
        }

        result = new StringBuilder(input.substring(pointer) + result.toString() + "ay");

        for (int i = 0; i < capitals.length; i++) {
            int capital = capitals[i];
            if (capital == 1) {
                result.setCharAt(i, Character.toUpperCase(result.charAt(i)));
            }
        }

        return result.toString();
    }

    // Add additonal private methods here.
    // For example, I had one like this:
    // private static String capitalizeFirstLetter(String input)

}
