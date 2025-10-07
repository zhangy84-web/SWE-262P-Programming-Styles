import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
    	Set<String> stopWords = getStopWords();
    	Hashtable<String, Integer> wordFrequency = new Hashtable<String, Integer>();

    	// Read files line by line.
    	// ignore ,./etc, ignore numbers.
        String filename = args[0];
        File file = new File(filename);
        
        // Remove everything other than words and '.
        Pattern wordDelimiter = Pattern.compile("[^a-zA-Z']+");

        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter(wordDelimiter);
            
            while (scanner.hasNext()) {
                // Convert to lower case for clean comparison.
                String word = scanner.next().toLowerCase();
                if (!stopWords.contains(word)) {
                	// Remove 's like it's -> it.
                	if (word.endsWith("\'s") && word.length() > 2)
                		word = word.substring(0, word.length() - 2);
                	// Remove extra '.
                	word = word.replace("\'", "");
                	// Count using HashTable.
                	int previousFrequency = wordFrequency.getOrDefault(word, 0);
                	wordFrequency.put(word, previousFrequency + 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found at " + filename);
        }

        // Filter based on homework test example.
        List<Map.Entry<String, Integer>> filteredWordFrequency = new LinkedList<>(wordFrequency.entrySet());
        filteredWordFrequency.removeIf(entry -> entry.getValue() <= 200);
        Collections.sort(filteredWordFrequency, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // To sort from largest to smallest.
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        // Finally, print output.
        for (Map.Entry<String, Integer> entry : filteredWordFrequency) {
        	String word = entry.getKey();
        	int frequency = entry.getValue();
        	if (frequency > 200)
        		System.out.println(word + " - " + frequency);
        }
    }

    // Get stop words from predefined location.
    private static Set<String> getStopWords() {
    	Set<String> stopWords = new HashSet<>();

    	// Fixed location.
    	Path filePath = Paths.get("../stop_words.txt");
    	try {
    		String content = Files.readString(filePath);
        	String[] words = content.split(",");
        	for (String word : words) {
        		stopWords.add(word);
        	}
    	} catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    	return stopWords;
    }
}