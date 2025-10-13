package piglatin;

public class TestSuite {
    // Run a bunch of basic tests on PigLatinTranslator
    public static int run() {
        int score = 0;
        int total = 12;

        // Single Words
        // Vowel first letter
        score += basicTest("eat", "eatay");
        // Single consonant
        score += basicTest("pig", "igpay");
        // Double consonant
        score += basicTest("trash", "ashtray");

        // "null" :)
        score += basicTest("null", "ullnay");

        // Capitalization (keep caps as-is except transfer first letter style)
        score += basicTest("Trash", "Ashtray");
        score += basicTest("trash", "ashtray");
        score += basicTest("TrAsH", "AsHtray");

        // Punctuation
        score += basicTest("Trash.", "Ashtray.");
        score += basicTest("clean-cut", "ean-cutclay");

        score += basicTest("", "");
        score += basicTest("    ", "");

        // Multiple words
        score += basicTest("pigs eat trash", "igspay eatay ashtray");

        if (score >= total) {
            System.out.println("--- TEST PASSED! Congrats! ---");
        } else {
            System.out.println("--- TEST FAILED! :( Score: " + score + "/" + total + " ---");
        }

        return score;
    }

    public static int basicTest(String input, String expected) {
        String result = PigLatinTranslator.translate(input);
        if (result.equals(expected)) {
            System.out.println(" PASS: '" + input + "' -> '" + expected + "'");
            return 1;
        } else {
            System.out.println(" FAIL: '" + input + "', '" + result + "' != '" + expected + "'");
            return 0;
        }
    }
}
