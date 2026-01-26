package sorting;

public class InsertionSort implements Sorter {

    public void sort(int[] input) {
        for (int lock = 0; lock < input.length; lock++) {
            int comp = input[lock];
            for (int i = lock; i < input.length; i++) {
                if (input[i] >= comp) {
                    continue;
                }
                int temp = input[lock];
                input[lock] = input[i];
                input[i] = temp;
            }
        }

    }
}
