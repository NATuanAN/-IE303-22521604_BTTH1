import java.util.*;

class Point implements Comparable<Point> {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // So sánh điểm theo y trước, nếu bằng thì so sánh theo x
    @Override
    public int compareTo(Point p) {
        return (this.y == p.y) ? Integer.compare(this.x, p.x) : Integer.compare(this.y, p.y);
    }
}

public class GrahamScan {
    // Tính tích có hướng giữa vector OA và OB
    // Kết quả > 0: quay trái, < 0: quay phải, = 0: thẳng hàng
    public static int crossProduct(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }

    public static List<Point> grahamScan(List<Point> points) {
        if (points.size() < 3)
            return points; // Không thể tạo bao lồi nếu < 3 điểm

        // Bước 1: Tìm điểm thấp nhất (P0)
        Collections.sort(points);
        Point P0 = points.get(0);

        // Bước 2: Sắp xếp theo góc cực với P0
        points.sort((a, b) -> {
            double angleA = Math.atan2(a.y - P0.y, a.x - P0.x);
            double angleB = Math.atan2(b.y - P0.y, b.x - P0.x);
            return Double.compare(angleA, angleB);
        });

        // Bước 3: Duyệt các điểm, xây dựng bao lồi
        Stack<Point> stack = new Stack<>();
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

        return new ArrayList<>(stack); // Chuyển stack thành danh sách kết quả
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
