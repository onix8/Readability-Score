package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String[] sentences = text.split("[.!?]+");
        int countSentences = sentences.length;
        int[] countWords = new int[countSentences];
        String[][] sentencesSplitAnWords = new String[countSentences][];

        for (int i = 0; i < countSentences; i++) {
            String sentence = sentences[i];
            sentencesSplitAnWords[i] = sentence.replaceAll("\\W", " ").split("\\s+");
            int counterWords = 0;
            for (int j = 0; j < sentencesSplitAnWords[i].length; j++) {
                String word = sentencesSplitAnWords[i][j];
                if (word.matches("^\\w*\\w$")) {
                    counterWords++;
                }
            }
            countWords[i] = counterWords;
        }

        int sumCountAllWords = 0;

        for (int count : countWords) {
            sumCountAllWords += count;
        }

        double averageCountWordsInSentence = ((double) sumCountAllWords) / ((double) countSentences);

        String readability = averageCountWordsInSentence > 10 ? "HARD" : "EASY";
        System.out.println(readability);
    }
}
