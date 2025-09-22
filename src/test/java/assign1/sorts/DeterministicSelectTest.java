package assign1.sorts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import assign1.metrics.Metrics;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {

    @Test
    public void testRandomArrays() {
        Random rnd = new Random(42);
        for (int t = 0; t < 50; t++) {
            int n = rnd.nextInt(200);
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(10000);

            for (int k = 0; k < n; k++) {
                Integer[] copy = Arrays.copyOf(a, n);
                Metrics m = new Metrics();
                Integer kth = DeterministicSelect.select(copy, k, m);

                Integer[] expected = Arrays.copyOf(a, n);
                Arrays.sort(expected);
                assertEquals(expected[k], kth, "Mismatch at k=" + k + " for array size " + n);
            }
        }
    }

    @Test
    public void testEdgeCases() {
        Metrics m = new Metrics();

        Integer[] empty = new Integer[0];
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(empty, 0, m));

        Integer[] one = new Integer[]{5};
        assertEquals(5, DeterministicSelect.select(one, 0, m));

        Integer[] two = new Integer[]{9, 3};
        assertEquals(3, DeterministicSelect.select(two, 0, m));
        assertEquals(9, DeterministicSelect.select(two, 1, m));
    }
}
