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


@FunctionalInterface
interface Continuation<T, R> {
    R apply(T result);
}


public class Nine {
    public static void main(String[] args) {
        readData(args[0], (data) -> {
            return Nine.filterAndNormalize(data, (filteredData) -> {
                return Nine.splitWords(filteredData, (words) -> {
                    return Nine.removeStopWords(words, (filteredWords) -> {
                        return Nine.countFrequencies(filteredWords, (wordFrequency) -> {
                            return Nine.sort(wordFrequency, (sortedWordFrequency) -> {
                                return Nine.printAll(sortedWordFrequency);
                            });
                        });
                    });
                });
            });
        });
    }

    private static <R> R readData(String filename, Continuation<String, R> k) {
        Path filePath = Paths.get(filename);
        String data = "";
        try {
            data = Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return k.apply(data);
    }

    private static <R> R filterAndNormalize(String data, Continuation<String, R> k) {
        return k.apply(data.replaceAll("[^a-zA-Z]", " ").toLowerCase());
    }

    private static <R> R splitWords(String data, Continuation<String[], R> k) {
        return k.apply(data.split("\\s+"));
    }

    private static <R> R removeStopWords(String[] wordArray, Continuation<List<String>, R> k) {
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
        return k.apply(words);
    }

    private static <R> R countFrequencies(List<String> words, Continuation<Map<String, Integer>, R> k) {
        Map<String, Integer> wordFrequency = new Hashtable<String, Integer>();
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return k.apply(wordFrequency);
    }

    private static <R> R sort(Map<String, Integer> wordFrequency, Continuation<List<Map.Entry<String, Integer>>, R> k) {
        List<Map.Entry<String, Integer>> wordFrequencyList = new LinkedList<>(wordFrequency.entrySet());
        Collections.sort(wordFrequencyList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // To sort from largest to smallest.
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        return k.apply(wordFrequencyList);
    }

    private static List<Map.Entry<String, Integer>> printAll(List<Map.Entry<String, Integer>> wordFrequency) {
        int max = Math.min(wordFrequency.size(), 25);
        for (int i = 0; i < max; i++) {
            System.out.println(wordFrequency.get(i).getKey() + " - " + wordFrequency.get(i).getValue());
        }
        return wordFrequency;
    }
}