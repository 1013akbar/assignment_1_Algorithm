package assign1.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    void testTwoPoints() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4)  // distance = 5
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(5.0, result, 1e-9, "Distance between two points should be 5");
    }

    @Test
    void testThreePointsCollinear() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(5, 0),
                new ClosestPair.Point(10, 0)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(5.0, result, 1e-9, "Closest pair should be distance 5");
    }

    @Test
    void testFourPointsSquare() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(0, 1),
                new ClosestPair.Point(1, 0),
                new ClosestPair.Point(1, 1)
        };
        double result = ClosestPair.closestPair(points);
        assertEquals(1.0, result, 1e-9, "Closest pair in square should be 1");
    }

    @Test
    void testRandomPoints() {
        ClosestPair.Point[] points = {
                new ClosestPair.Point(2, 3),
                new ClosestPair.Point(12, 30),
                new ClosestPair.Point(40, 50),
                new ClosestPair.Point(5, 1),
                new ClosestPair.Point(12, 10),
                new ClosestPair.Point(3, 4)
        };
        double result = ClosestPair.closestPair(points);
        // Expected closest pair: (2,3) and (3,4) => sqrt(2)
        assertEquals(Math.sqrt(2), result, 1e-9, "Closest distance should be sqrt(2)");
    }

    @Test
    void testLargeInput() {
        int n = 1000;
        ClosestPair.Point[] points = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new ClosestPair.Point(i, i * 2);
        }
        double result = ClosestPair.closestPair(points);
        // Distance between consecutive points ≈ sqrt((1^2)+(2^2)) = sqrt(5)
        assertEquals(Math.sqrt(5), result, 1e-9, "Closest distance should be sqrt(5)");
    }
}
