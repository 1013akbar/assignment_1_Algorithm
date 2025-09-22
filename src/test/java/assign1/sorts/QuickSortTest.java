package assign1.sorts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import assign1.metrics.Metrics;
import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {

    @Test
    public void testRandomSmallArrays() {
        Random rnd = new Random(42);
        for (int t = 0; t < 50; t++) {
            int n = rnd.nextInt(200);
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10000);
            Integer[] expected = Arrays.copyOf(a, a.length);
            Arrays.sort(expected);

            Metrics m = new Metrics();
            QuickSort.sort(a, m);

            assertArrayEquals(expected, a, "Mismatch on random array of size " + n);
        }
    }

    @Test
    public void testEdgeCases() {
        Metrics m = new Metrics();

        Integer[] empty = new Integer[0];
        QuickSort.sort(empty, m);
        assertEquals(0, empty.length);

        Integer[] one = new Integer[]{5};
        QuickSort.sort(one, m);
        assertArrayEquals(new Integer[]{5}, one);

        Integer[] two = new Integer[]{9, 3};
        QuickSort.sort(two, m);
        assertArrayEquals(new Integer[]{3, 9}, two);
    }
}
