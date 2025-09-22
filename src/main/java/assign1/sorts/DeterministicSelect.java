package assign1.sorts;

import assign1.metrics.Metrics;
import java.util.Arrays;

/**
 * Deterministic Select (Median-of-Medians, O(n)) implementation.
 * - Groups of 5
 * - In-place partition
 * - Recurse only into the side containing the k-th element
 * - Prefer smaller side for recursion
 */
public final class DeterministicSelect {

    private DeterministicSelect() { /* no instances */ }

    public static <T extends Comparable<? super T>> T select(T[] a, int k, Metrics m) {
        if (a == null || k < 0 || k >= a.length)
            throw new IllegalArgumentException("Invalid input or k");
        if (m == null) throw new IllegalArgumentException("Metrics must not be null");
        m.reset();
        return select(a, 0, a.length - 1, k, m);
    }

    private static <T extends Comparable<? super T>> T select(T[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            int n = hi - lo + 1;
            if (n <= 5) {
                insertionSort(a, lo, hi, m);
                return a[lo + k];
            }

            // Step 1: Divide into groups of 5 and find medians
            int numMedians = (n + 4) / 5;
            for (int i = 0; i < numMedians; i++) {
                int subLo = lo + i * 5;
                int subHi = Math.min(subLo + 4, hi);
                insertionSort(a, subLo, subHi, m);
                swap(a, lo + i, subLo + (subHi - subLo) / 2);
            }

            // Step 2: Recursively find median of medians
            T pivot = select(a, lo, lo + numMedians - 1, numMedians / 2, m);

            // Step 3: Partition around pivot
            int pivotIndex = partition(a, lo, hi, pivot, m);

            int leftSize = pivotIndex - lo;
            if (k == leftSize) return a[pivotIndex];
            else if (k < leftSize) {
                hi = pivotIndex - 1;
            } else {
                k = k - leftSize - 1;
                lo = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int lo, int hi, T pivot, Metrics m) {
        int pivotIndex = lo;
        for (int i = lo; i <= hi; i++) {
            m.incComparisons(1);
            if (a[i].compareTo(pivot) == 0) {
                pivotIndex = i;
                break;
            }
        }
        swap(a, pivotIndex, hi);
        pivotIndex = lo;

        for (int i = lo; i < hi; i++) {
            m.incComparisons(1);
            if (a[i].compareTo(pivot) < 0) {
                swap(a, i, pivotIndex);
                pivotIndex++;
            }
        }
        swap(a, pivotIndex, hi);
        return pivotIndex;
    }

    private static <T extends Comparable<? super T>> void insertionSort(T[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            T key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.incComparisons(1);
                if (a[j].compareTo(key) > 0) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
