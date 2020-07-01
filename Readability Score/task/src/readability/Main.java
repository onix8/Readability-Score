package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The program outputs the contents of a text file and analyzes it: outputs
 * statistics, readability index, and the recommended age for the reader.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args path to the text file
     */
    public static void main(String[] args) {
        Readability readability = new Readability(readInputFile(args[0]));
        readability.printText();
        readability.calculateStats();
        readability.printStats();
        readability.calculateReadability(readability.choosingAnAlgorithm());
    }

    /**
     * Reads text from a file.
     *
     * @param pathToFile a string with the text
     * @return text from a file
     */
    private static String readInputFile(String pathToFile) {
        String text = "No file found: " + pathToFile;
        File file = new File(pathToFile);

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            text = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            System.out.println(text);
        }
        return text;
    }
}
