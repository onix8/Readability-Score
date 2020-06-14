package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        int length = text.length();
        System.out.print(length <= 100 ? "EASY" : "HARD");
    }
}
