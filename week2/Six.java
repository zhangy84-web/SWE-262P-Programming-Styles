import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Six {
    public static void main(String[] args) {
    	printAll(sort(countFrequencies(removeStopWords(splitWords(filterAndNormalize(readData(args[0])))))));
    }

    private static String readData(String filename) {
        Path filePath = Paths.get(filename);
        String data = "";
        try {
            data = Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return data;
    }

    private static String filterAndNormalize(String data) {
        return data.replaceAll("[^a-zA-Z]", " ").toLowerCase();
    }

    private static String[] splitWords(String data) {
        return data.split("\\s+");
    }

    private static List<String> removeStopWords(String[] wordArray) {
        Set<String> stopWords = new HashSet<>();
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
        List<String> words = new ArrayList<>(Arrays.asList(wordArray));
        words.removeAll(stopWords);
        return words;
    }

    private static Map<String, Integer> countFrequencies(List<String> words) {
        Map<String, Integer> wordFrequency = new Hashtable<String, Integer>();
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return wordFrequency;
    }

    private static List<Map.Entry<String, Integer>> sort(Map<String, Integer> wordFrequency) {
        List<Map.Entry<String, Integer>> wordFrequencyList = new LinkedList<>(wordFrequency.entrySet());
        Collections.sort(wordFrequencyList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // To sort from largest to smallest.
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        return wordFrequencyList;
    }

    private static void printAll(List<Map.Entry<String, Integer>> wordFrequency) {
        int max = Math.min(wordFrequency.size(), 25);
        for (int i = 0; i < max; i++) {
            System.out.println(wordFrequency.get(i).getKey() + " - " + wordFrequency.get(i).getValue());
        }
    }
}