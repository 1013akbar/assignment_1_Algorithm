package assign1.geometry;

import java.util.*;

public class ClosestPair {

    // Point class
    public static class Point {
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Main entry method
    public static double closestPair(Point[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }

        Point[] pointsSortedX = points.clone();
        Point[] pointsSortedY = points.clone();

        Arrays.sort(pointsSortedX, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(pointsSortedY, Comparator.comparingDouble(p -> p.y));

        return closestPairRec(pointsSortedX, pointsSortedY, 0, points.length - 1);
    }

    // Recursive divide & conquer
    private static double closestPairRec(Point[] pointsSortedX, Point[] pointsSortedY, int left, int right) {
        int n = right - left + 1;

        // ✅ Base case: use brute force for small inputs
        if (n <= 3) {
            return bruteForce(pointsSortedX, left, right);
        }

        int mid = left + (right - left) / 2;
        Point midPoint = pointsSortedX[mid];

        // Split Y into leftY and rightY arrays for recursion
        List<Point> leftY = new ArrayList<>();
        List<Point> rightY = new ArrayList<>();
        for (Point p : pointsSortedY) {
            if (p.x <= midPoint.x) {
                leftY.add(p);
            } else {
                rightY.add(p);
            }
        }

        double dl = closestPairRec(pointsSortedX, leftY.toArray(new Point[0]), left, mid);
        double dr = closestPairRec(pointsSortedX, rightY.toArray(new Point[0]), mid + 1, right);

        double d = Math.min(dl, dr);

        // Build strip of points close to the dividing line
        List<Point> strip = new ArrayList<>();
        for (Point p : pointsSortedY) {
            if (Math.abs(p.x - midPoint.x) < d) {
                strip.add(p);
            }
        }

        return Math.min(d, stripClosest(strip, d));
    }

    // ✅ Brute force for small cases
    private static double bruteForce(Point[] points, int left, int right) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    // ✅ Check points in strip (at most 7 neighbors per point)
    private static double stripClosest(List<Point> strip, double d) {
        double min = d;
        int n = strip.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && (strip.get(j).y - strip.get(i).y) < min; j++) {
                double dist = distance(strip.get(i), strip.get(j));
                if (dist < min) {
                    min = dist;
                }
            }
        }
        return min;
    }

    // Euclidean distance
    private static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
