package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The program outputs the contents of a text file and analyzes it: outputs
 * statistics, the automated readability index (ARI), and the recommended age
 * for the reader.
 */
public class Main {
    private static final String[] approximateAges = new String[]{"5-6", "6-7", "7-9", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16", "16-17", "17-18", "18-24", "24+"};
    private static String text = "";
    private static double words;
    private static double sentences;
    private static double characters;
    private static double score;
    private static String approximateAge;

    /**
     * The entry point of application.
     *
     * @param args path to the text file
     */
    public static void main(String[] args) {
        readInputFile(args[0]);
        printText();
        calculateReadability();
        printStatistics();
    }

    /**
     * Calculate readability.
     */
    private static void calculateReadability() {
        int countCharacters = 0;
        int countSentences;
        int countWords = 0;
        int score;
        String[] sentences;
        String[][] sentencesSplitAnWords;

        sentences = text.split("(?<=\\S[.!?])\\s");
        countSentences = sentences.length;
        sentencesSplitAnWords = new String[countSentences][];

        for (int i = 0; i < countSentences; i++) {
            String sentence = sentences[i];
            sentencesSplitAnWords[i] = sentence.split("\\s+");
            countWords += sentencesSplitAnWords[i].length;
            for (int j = 0; j < sentencesSplitAnWords[i].length; j++) {
                countCharacters += sentencesSplitAnWords[i][j].length();
            }
        }

        Main.sentences = countSentences;
        words = countWords;
        characters = countCharacters;
        Main.score = 4.71 * (characters / words) + 0.5 * (words / Main.sentences) - 21.43;
        score = (int) Math.ceil(Main.score);
        approximateAge = score <= 1 ? approximateAges[0] :
                score <= 13 ? approximateAges[score - 1] : approximateAges[13];
    }

    /**
     * Displays statistics on the screen.
     */
    private static void printStatistics() {
        System.out.println("Words: " + (int) words);
        System.out.println("Sentences: " + (int) sentences);
        System.out.println("Characters: " + (int) characters);
        System.out.printf("The score is: %.2f%n", score);
        System.out.println("This text should be understood by " + approximateAge + " year olds.");
    }

    /**
     * Displays text on the screen.
     */
    private static void printText() {
        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();
    }

    /**
     * Reads text from a file.
     *
     * @param pathToFile a string with the text
     */
    private static void readInputFile(String pathToFile) {
        File file = new File(pathToFile);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                text = new StringBuilder().append(text).append(scanner.nextLine()).append("\n").toString();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + pathToFile);
        }
    }
}
