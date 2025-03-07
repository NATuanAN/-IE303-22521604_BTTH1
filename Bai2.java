import java.util.Scanner;

public class Bai2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int r = sc.nextInt();
        System.out.println(pi_num());
    }

    static int n = 1000000;// n tien den vo cung

    static double pi_num() {
        double pi = 0;
        for (int i = 0; i < n; i++) {
            pi += Math.pow(-1, i) / (2 * i + 1);
        }

        return pi * 4;
    }

    static double dien_tich(int r) {
        return Math.pow(r, r) * pi_num();
    }
}