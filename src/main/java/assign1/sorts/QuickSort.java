package assign1.sorts;

import assign1.metrics.Metrics;
import java.util.Random;

/**
 * QuickSort implementation with:
 * - randomized pivot
 * - recurse on smaller partition first (bounded recursion depth)
 * - metrics tracking (comparisons, max recursion depth)
 */
public final class QuickSort {
    private static final int INSERTION_CUTOFF = 32;
    private static final Random rnd = new Random();

    private QuickSort() { /* no instances */ }

    public static <T extends Comparable<? super T>> void sort(T[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        if (m == null) throw new IllegalArgumentException("Metrics must not be null");
        m.reset();
        quickSort(a, 0, a.length - 1, m);
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= INSERTION_CUTOFF) {
                insertionSort(a, lo, hi, m);
                return;
            }

            m.push();
            int pivotIndex = partition(a, lo, hi, m);
            m.pop();

            // Recurse on smaller side first, iterate on larger
            if (pivotIndex - lo < hi - pivotIndex) {
                quickSort(a, lo, pivotIndex - 1, m);
                lo = pivotIndex + 1; // tail recursion elimination
            } else {
                quickSort(a, pivotIndex + 1, hi, m);
                hi = pivotIndex - 1; // tail recursion elimination
            }
        }
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int lo, int hi, Metrics m) {
        int pivotIndex = lo + rnd.nextInt(hi - lo + 1);
        swap(a, pivotIndex, hi);
        T pivot = a[hi];

        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.incComparisons(1);
            if (a[j].compareTo(pivot) <= 0) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, hi);
        return i;
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
