package sorting;

import java.util.Arrays;

public class TestSuite
{
    // Run a bunch of basic tests on your sorting methods
    public static void run(int[] testInput, int loops)
    {
        boolean pass = true;

        System.out.println("Running test with input: " + Arrays.toString(testInput));

        BubbleSort b = new BubbleSort();
        pass &= basicTest(b, testInput, loops);

        MergeSort m = new MergeSort();
        pass &= basicTest(m, testInput, loops);

        SelectionSort s = new SelectionSort();
        pass &= basicTest(s, testInput, loops);

        InsertionSort i = new InsertionSort();
        pass &= basicTest(i, testInput, loops);

        if (pass == true)
        {
            System.out.println("--- TEST PASSED! Congrats! ---");
        }
        else
        {
            System.out.println("--- TEST FAILED! :( ---");
        }
    }

    public static boolean basicTest(Sorter s, int[] unsorted, int loops)
    {
        boolean result = false;
        String className = s.getClass().getSimpleName();
        System.out.println("\n--- Testing: " + className + " ---");

        // Save the start time of the test loop.
        long startTime = System.nanoTime();
        for (int x = 0; x < loops; x++) {
            // Copy the unsorted array to avoid sorting it.
            int[] test = unsorted.clone();
            // Sort the test array.
            s.sort(test);
            // Check the results
            result = checkResults(test, unsorted);
        }
        long endTime = System.nanoTime();

        // Compute the duration in seconds.
        double duration = (endTime - startTime) / 1000000000.0;
        // Print results
        System.out.println(className + " took: " + duration + " seconds for " + loops + " loops.");

        return result;
    }

    public static boolean checkResults(int[] sorted, int[] unsorted)
    {
        // Uncomment for debug.
        //System.out.println("    Unsorted: " + Arrays.toString(unsorted));
        //System.out.println("    Sorted:   " + Arrays.toString(sorted));

        // Ensure that the array length is the same.
        if (sorted.length != unsorted.length)
        {
            System.out.println("Error! Mismatching lengths.");
            return false;
        }

        // Traverse the array, looking for elements that are out of order.
        for (int i = 0; i < sorted.length - 1; i++) {
            // If we find a single out of order element, then stop - the array is UNSORTED!
            if (sorted[i] > sorted[i + 1]) {
                System.out.println("Error! sorted[" + i + "] (" + sorted[i] + " > sorted[" + i + 1 + "] (" + sorted[i + 1] + ")");
                return false;
            }
        }
        return true;
    }
}
