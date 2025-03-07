import java.util.*;

public class Bai3 {
    // Lớp Point để lưu tọa độ trạm phát sóng
    static class Point implements Comparable<Point> {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // So sánh điểm theo x, nếu x bằng thì so sánh theo y
        public int compareTo(Point p) {
            return this.x == p.x ? Integer.compare(this.y, p.y) : Integer.compare(this.x, p.x);
        }

        // In điểm
        public String toString() {
            return x + " " + y;
        }
    }

    // Hàm xác định hướng quay (tích có hướng)
    private static int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        return Integer.compare(val, 0); // Trả về -1 nếu ngược chiều kim đồng hồ, 1 nếu cùng chiều, 0 nếu thẳng hàng
    }

    // Tìm bao lồi bằng thuật toán Monotone Chain
    public static List<Point> convexHull(Point[] points) {
        Arrays.sort(points); // Sắp xếp theo x, nếu bằng thì theo y

        List<Point> lower = new ArrayList<>();
        for (Point p : points) {
            while (lower.size() >= 2 && orientation(lower.get(lower.size() - 2), lower.get(lower.size() - 1), p) <= 0) {
                lower.remove(lower.size() - 1);
            }
            lower.add(p);
        }

        List<Point> upper = new ArrayList<>();
        for (int i = points.length - 1; i >= 0; i--) {
            Point p = points[i];
            while (upper.size() >= 2 && orientation(upper.get(upper.size() - 2), upper.get(upper.size() - 1), p) <= 0) {
                upper.remove(upper.size() - 1);
            }
            upper.add(p);
        }

        // Xóa trùng điểm cuối cùng
        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);

        // Kết hợp hai phần lại
        lower.addAll(upper);
        return lower;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Số trạm phát sóng
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[i] = new Point(x, y);
        }

        List<Point> hull = convexHull(points);

        // In kết quả (các trạm cảnh báo)
        for (Point p : hull) {
            System.out.println(p);
        }
    }
}
