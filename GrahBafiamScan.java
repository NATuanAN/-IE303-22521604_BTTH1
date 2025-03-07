import java.util.*;

class Point implements Comparable<Point> {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        return (this.y == p.y) ? Integer.compare(this.x, p.x) : Integer.compare(this.y, p.y);
    }
}

public class GrahBafiamScan {
    public static int crossProduct(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    public static List<Point> grahamScan(List<Point> points) {
        if (points.size() < 3)
            return points;

        Collections.sort(points);
        Point P0 = points.get(0);

        points.sort((a, b) -> {
            double angleA = Math.atan2(a.y - P0.y, a.x - P0.x);
            double angleB = Math.atan2(b.y - P0.y, b.x - P0.x);
            return Double.compare(angleA, angleB);
        });
        Stack<Point> stack = new Stack<>();
        stack.push(points.get(0));
        stack.push(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            Point top = stack.pop();
            while (!stack.isEmpty() & crossProduct(stack.peek(), top, points.get(i)) <= 0) {
                top = stack.pop();
            }
            stack.push(top);
            stack.push(points.get(i));
        }

        return new ArrayList<>(stack);
    }

    public static void main(String[] args) {
        List<Point> points = Arrays.asList(
                new Point(0, 0), new Point(1, 1), new Point(2, 2),
                new Point(3, 0), new Point(2, -1), new Point(1, -1));

        List<Point> hull = grahamScan(points);

        System.out.println("Bao lồi:");
        for (Point p : hull) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }
}
