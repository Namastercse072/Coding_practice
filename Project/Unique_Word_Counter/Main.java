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
        }

        Set<String> uniqueWords = new HashSet<>();
        for (String[] words : textArray) {
            for (String word : words) {
                if (!word.isEmpty()) {
                    uniqueWords.add(word.toLowerCase());
                }
            }
        }

        System.out.println("Unique word count: " + uniqueWords.size());
// ...existing code...
    }
}