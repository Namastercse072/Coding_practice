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
        System.out.println("Word count: ");
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < textArray.length; i++) {
            for (int j = 0; j < textArray[i].length; j++) {
                // Convert each word to lowercase and remove punctuation
                String word = textArray[i][j];
                String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();    
                // System.out.println("Word: " + cleanedWord);
               
                if (!cleanedWord.isEmpty()) {
                    wordCount.put(cleanedWord, wordCount.getOrDefault(cleanedWord, 0) + 1);
                }
            } 
        }
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                // Print each unique word and its count
            System.out.println(entry.getKey() + ": " + entry.getValue());     
        }
    }
}