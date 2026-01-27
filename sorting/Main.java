package sorting;

public class Main {
    public static void main(String[] args) {
        // Task:
        // 1. Implement missing sorting algorithms.
        // - InsertionSort.java
        // - SelectionSort.java
        //
        // 2. Add a method to class Main to generate a random array.
        // 3. Provide random input to test suite and ensure your implementations
        // of InsertionSort and Selection sort continue to work.
        // 4. Increase the loop count for TestSuite to get better performance results.

        int[] testInput = randomArray(5);

        // Increase the loop count to get better results once it works.
        // TestSuite.run(testInput, 10000);
        TestSuite.run(testInput, 1);
    }

    public static int[] randomArray(int length) {
        // int min = 0;
        // int max = 100;
        // int[] a = new int[length];
        // for (int i = 0; i < length; i++) {
        // a[i] = (int) (Math.random() * (max - min)) + min;
        // }

        return java.util.stream.IntStream
                .generate(() -> (int) (Math.random() * 100))
                .limit(length)
                .toArray();
    }

}
