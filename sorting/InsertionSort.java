package sorting;

public class InsertionSort implements Sorter {

    public void sort(int[] input) {
        for (int i = 0; i < input.length; i++) {
            int val = input[i];
            int insertAt = 0;
            while (val >= input[insertAt] && insertAt < i) {
                insertAt++;
            }
            int temp = input[i];
            // rotate from indices insertAt -> i
            for (int j = insertAt; j < i; j++) {
                input[j + 1] = input[j];
            }
            input[insertAt] = temp;
        }
    }
}
