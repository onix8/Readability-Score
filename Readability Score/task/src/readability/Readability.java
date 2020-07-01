package readability;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Readability of the text.
 */
public class Readability {
    private final String text;
    private double words;
    private double sentences;
    private double characters;
    private double syllables;
    private double polysyllables;
    private int approximateAgeARI;
    private int approximateAgeFK;
    private int approximateAgeSMOG;
    private int approximateAgeCL;

    /**
     * Instantiates a new Readability.
     *
     * @param text the text
     */
    Readability(String text) {
        this.text = text;
    }


    /**
     * Displays text on the screen.
     */
    void printText() {
        System.out.println("The text is:");
        System.out.println(text);
    }

    /**
     * Calculates the statistics of the text.
     */
    void calculateStats() {
        int countCharacters = 0;
        int countSentences;
        int countWords = 0;
        int countSyllables = 0;
        int countPolysyllables = 0;
        int countVowels;
        String[] arraySentences;
        String[][] sentencesSplitAnWords;

        arraySentences = text.split("(?<=\\S[.!?])\\s");
        countSentences = arraySentences.length;
        sentencesSplitAnWords = new String[countSentences][];

        for (int i = 0; i < countSentences; i++) {
            String sentence = arraySentences[i];
            sentencesSplitAnWords[i] = sentence.split("\\s+");
            countWords += sentencesSplitAnWords[i].length;
            for (int j = 0; j < sentencesSplitAnWords[i].length; j++) {
                countVowels = 0;
                String word = sentencesSplitAnWords[i][j];
                countCharacters += word.length();
                word = word.split("\\W+$")[0];
                if (word.toLowerCase().charAt(word.length() - 1) == 'e') {
                    word = word.substring(0, word.length() - 1);
                }
                Pattern vowel = Pattern.compile("(?i)[aeiouy]+");
                Matcher m = vowel.matcher(word);
                while (m.find()) {
                    countVowels++;
                }
                if (countVowels <= 1) {
                    countSyllables++;
                } else {
                    countSyllables += countVowels;
                    if (countVowels > 2) {
                        countPolysyllables++;
                    }
                }
            }
        }

        sentences = countSentences;
        words = countWords;
        characters = countCharacters;
        syllables = countSyllables;
        polysyllables = countPolysyllables;
    }

    /**
     * Displays statistics on the screen.
     */
    void printStats() {
        System.out.println("Words: " + (int) words);
        System.out.println("Sentences: " + (int) sentences);
        System.out.println("Characters: " + (int) characters);
        System.out.println("Syllables: " + (int) syllables);
        System.out.println("Polysyllables: " + (int) polysyllables);
    }

    /**
     * The user selects the algorithm.
     *
     * @return the string with the algorithm
     */
    String choosingAnAlgorithm() {
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner sc = new Scanner(System.in);
        String algorithm = sc.nextLine();
        System.out.println();
        return algorithm;
    }

    /**
     * Calculate readability.
     *
     * @param algorithm the algorithm
     */
    void calculateReadability(String algorithm) {
        switch (algorithm) {
            case "all":
                calculateAll();
                break;
            case "ARI":
                calculateAri();
                break;
            case "FK":
                calculateFk();
                break;
            case "SMOG":
                calculateSmog();
                break;
            case "CL":
                calculateCl();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + algorithm);
        }
    }

    /**
     * Calculate all indexes.
     */
    private void calculateAll() {
        calculateAri();
        calculateFk();
        calculateSmog();
        calculateCl();
        double approximateAge = (approximateAgeARI + approximateAgeFK + approximateAgeSMOG + approximateAgeCL) / 4.0;
        System.out.printf("%nThis text should be understood in average by %.2f year olds.%n", approximateAge);
    }

    /**
     * Automated Readability Index.
     */
    private void calculateAri() {
        double ari = 4.71 * (characters / words) + 0.5 * (words / sentences) - 21.43;
        approximateAgeARI = approximateAge(ari);
        System.out.printf("Automated Readability Index: %.2f (about %d year olds).%n", ari, approximateAgeARI);
    }

    /**
     * Flesch–Kincaid index.
     */
    private void calculateFk() {
        double fk = 0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
        approximateAgeFK = approximateAge(fk);
        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d year olds).%n", fk, approximateAgeFK);
    }

    /**
     * SMOG index.
     */
    private void calculateSmog() {
        double smog = 1.043 * Math.sqrt(polysyllables * (30 / sentences)) + 3.1291;
        approximateAgeSMOG = approximateAge(smog);
        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).%n", smog, approximateAgeSMOG);
    }

    /**
     * Coleman–Liau index.
     */
    private void calculateCl() {
        double cl = 0.0588 * (characters / words * 100) - 0.296 * (sentences / words * 100) - 15.8;
        approximateAgeCL = approximateAge(cl);
        System.out.printf("Coleman–Liau index: %.2f (about %d year olds).%n", cl, approximateAgeCL);
    }

    /**
     * Calculate approximate age.
     *
     * @param index readability index
     * @return approximate age
     */
    private int approximateAge(double index) {
        return (int) Math.ceil(index) + 6;
    }
}
