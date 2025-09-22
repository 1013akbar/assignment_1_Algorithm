package assign1.sorts;

import assign1.metrics.Metrics;

/**
 * MergeSort implementation with:
 * - reusable buffer (allocated once per top-level call)
 * - insertion sort cutoff for small arrays
 * - simple comparison counting and recursion-depth tracking via Metrics
 */
public final class MergeSort {
    private static final int INSERTION_CUTOFF = 32;

    private MergeSort() { /* no instances */ }

    public static <T extends Comparable<? super T>> void sort(T[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        if (m == null) throw new IllegalArgumentException("Metrics must not be null");
        m.reset();
        @SuppressWarnings("unchecked")
        T[] buf = (T[]) new Comparable[a.length];
        mergeSort(a, buf, 0, a.length - 1, m);
    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] a, T[] buf, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= INSERTION_CUTOFF) { 
            insertionSort(a, lo, hi, m); 
            return; 
        }

        int mid = (lo + hi) >>> 1;

        m.push();
        mergeSort(a, buf, lo, mid, m);
        m.pop();

        m.push();
        mergeSort(a, buf, mid + 1, hi, m);
        m.pop();

        m.incComparisons(1); // check if merge can be skipped
        if (a[mid].compareTo(a[mid + 1]) <= 0) return;

        merge(a, buf, lo, mid, hi, m);
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] buf, int lo, int mid, int hi, Metrics m) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            m.incComparisons(1);
            if (a[i].compareTo(a[j]) <= 0) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        while (i <= mid) buf[k++] = a[i++];
        while (j <= hi) buf[k++] = a[j++];
        for (k = lo; k <= hi; k++) a[k] = buf[k];
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
                } else {
                    break;
                }
            }
            a[j + 1] = key;
        }
    }
}
