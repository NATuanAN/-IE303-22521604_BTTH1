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

public class Bai3 {
    public static int crossProduct(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    public static List<Point> grahamScan(List<Point> points) {
        if (points.size() <= 3)
            return points;

        Set<Point> uniquePoints = new HashSet<>(points);
        points = new ArrayList<>(uniquePoints);

        Collections.sort(points);
        Point P0 = points.get(0);

        points.sort((a, b) -> {
            double angleA = Math.atan2(a.y - P0.y, a.x - P0.x);
            double angleB = Math.atan2(b.y - P0.y, b.x - P0.x);
            if (angleA == angleB)
                return Integer.compare((a.x - P0.x) * (a.x - P0.x) + (a.y - P0.y) * (a.y - P0.y),
                        (b.x - P0.x) * (b.x - P0.x) + (b.y - P0.y) * (b.y - P0.y));
            return Double.compare(angleA, angleB);
        });

        Deque<Point> stack = new ArrayDeque<>();
        stack.push(points.get(0));
        stack.push(points.get(1));

        for (int i = 2; i < points.size(); i++) {
            Point top = stack.pop();
            while (!stack.isEmpty() && crossProduct(stack.peek(), top, points.get(i)) <= 0) {
                top = stack.pop();
            }
            stack.push(top);
            stack.push(points.get(i));
        }

        return new ArrayList<>(stack);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap so diem: ");
        int n = sc.nextInt();
        List<Point> points = new ArrayList<>();

        System.out.println("Nhap so diem toa do");
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            points.add(new Point(x, y));
        }
        sc.close();

        List<Point> hull = grahamScan(points);

        System.out.println("Cac diem loi: ");
        for (Point p : hull) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }
}
