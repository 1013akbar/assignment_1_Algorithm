package assign1.metrics;

/**
 * Simple single-threaded metrics holder for recursion depth and comparison counts.
 */
public final class Metrics {
    private int depth = 0;
    private int maxDepth = 0;
    private long comparisons = 0L;

    public void reset() {
        depth = 0;
        maxDepth = 0;
        comparisons = 0L;
    }

    public void push() {
        depth++;
        if (depth > maxDepth) maxDepth = depth;
    }

    public void pop() {
        depth = Math.max(0, depth - 1);
    }

    public void incComparisons(long v) { comparisons += v; }

    public long getComparisons() { return comparisons; }

    public int getMaxDepth() { return maxDepth; }
}
