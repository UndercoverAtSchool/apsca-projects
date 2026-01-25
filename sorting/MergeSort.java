package sorting;

public class MergeSort implements Sorter {

    public void sort(int[] input) {
        // Start mergeSort by providing the whole array
        mergeSort(input, input.length);
    }

     /**
     *  Description of the Method
     *
     * @param  list  reference to an array of integers to be sorted
     */
    public void mergeSort(int [] list, int n){
        if (n < 2)
        {
            return;
        }
        // Split the array into two chunks
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        // Copy first half to l
        for (int i = 0; i < mid; i++)
        {
            l[i] = list[i];
        }
        // Copy second half to r
        for (int i = mid; i < n; i++)
        {
            r[i - mid] = list[i];
        }
        // Recursive call to mergeSort on split array
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        // merge the separate chunks
        merge(list, l, r, mid, n - mid);
    }

    public static void merge(int[] a, int[] l, int[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        // Copy elements from l and r into the output a
        // Copy the smaller element from each chunk first.
        while (i < left && j < right)
        {
            if (l[i] <= r[j])
            {
                a[k++] = l[i++];
            }
            else
            {
                a[k++] = r[j++];
            }
        }

        // Copy remaining elements to the output
        while (i < left)
        {
            a[k++] = l[i++];
        }
        while (j < right)
        {
            a[k++] = r[j++];
        }
    }
}
