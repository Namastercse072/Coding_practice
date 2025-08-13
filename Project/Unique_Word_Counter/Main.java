import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        
        String[] sentences = text.split("\\s+");
        String[][] textArray = new String[sentences.length][];
        // Split each sentence into words and store in the 2D array
        for (int i = 0; i < sentences.length; i++) {
            textArray[i] = sentences[i].trim().split(" ");
        }
       
        HashMap<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < textArray.length; i++) {
            for (int j = 0; j < textArray[i].length; j++) {
                // Convert each word to lowercase and remove punctuation
                String word = textArray[i][j];
                String cleanedWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();     
                if (!cleanedWord.isEmpty()) {
                    wordCount.put(cleanedWord, wordCount.getOrDefault(cleanedWord, 0) + 1);
                }
                
            } 
        }

        System.out.println("Total words: "+ sentences.length);
        System.out.println("Unique words: "+ wordCount.size());
        System.out.println("Word statistics:");

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                // Print each unique word and its count
            double percentage = (entry.getValue() * 100.0) / sentences.length;
            System.out.print(entry.getKey() + ": " + entry.getValue()+" ");
            System.out.printf("(%.2f%%)\n", percentage);
     
        }
    }
}