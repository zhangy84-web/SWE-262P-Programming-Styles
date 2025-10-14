import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Five {
	private static String data = "";
	private static Hashtable<String, Integer> wordFrequency = new Hashtable<String, Integer>();
	private static List<String> words = new ArrayList<String>();
	private static List<Map.Entry<String, Integer>> filteredWordFrequency = new LinkedList<>();

    public static void main(String[] args) {
    	readFile(Path.of(args[0]));
    	removeNonAlphabet();
    	scan();
    	removeStopWords();
    	freqs();
    	sort();
    	for (Map.Entry<String, Integer> entry : filteredWordFrequency) {
        	String word = entry.getKey();
        	int frequency = entry.getValue();
        	if (frequency > 200) {
        		System.out.println(word + " - " + frequency);
        	}
    	}
    }

    private static void readFile(Path filename) {
		//have access to a file 
    	try {
	        data = Files.readString(filename);
	    } catch (IOException e) {
	        e.printStackTrace();
		}
	}

	//replace all nonalphabetic chars in data with white space
	private static void removeNonAlphabet () {
		data = data.replaceAll("[^a-zA-Z]+", " ").toLowerCase();
	}

	//scan data for words
	private static void scan() {
		String[] splitWords = data.split("\\s+");
		words.addAll(Arrays.asList(splitWords));
	}

	private static void removeStopWords () {
		Set<String> stopWords = new HashSet<>();

    	// Fixed location.
    	Path filePath = Paths.get("../stop_words.txt");
    	try {
    		String content = Files.readString(filePath);
        	String[] sWords = content.split(",");
        	for (String sWord : sWords) {
        		stopWords.add(sWord);
        	}
    	} catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        words.removeAll(stopWords);
	}

	private static void freqs () {
		for (String word : words) {
	        int previousFrequency = wordFrequency.getOrDefault(word, 0);
	        wordFrequency.put(word, previousFrequency + 1);
	    }
	}

	private static void sort () {
    	filteredWordFrequency.addAll(wordFrequency.entrySet());
        filteredWordFrequency.removeIf(entry -> entry.getValue() <= 200);
        Collections.sort(filteredWordFrequency, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // To sort from largest to smallest.
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
    }
}