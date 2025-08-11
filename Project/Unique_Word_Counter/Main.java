// import Coding_practice.Project.Unique_Word_Counter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        
        String[] sentences = text.split("\\.");
        String[][] textArray = new String[sentences.length][];
        // Split each sentence into words and store in the 2D array
        for (int i = 0; i < sentences.length; i++) {
            textArray[i] = sentences[i].trim().split(" ");
            // System.out.println("Sentence " + (i + 1) + ": " + Arrays.toString(textArray[i]));
        }

        for (int i = 0; i < textArray.length; i++) {
            for (int j = 0; j < textArray[i].length; j++) {
                // Convert each word to lowercase and remove punctuation
                String word = textArray[i][j];
                String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();    
                if (!word.isEmpty()) {
                    // Print the unique word
                    System.out.println("Word[" + i + "," + j + "]: " + word);
                    System.out.println("Word[" + i + "," + j + "]: " + cleanedWord);
                }   
            }
        }
    }
}