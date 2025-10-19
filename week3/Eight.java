import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Eight {
    public static void main(String[] args) {
        List<String> words = new LinkedList<>();
        Set<String> stopWords = getStopWords();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            parse(reader, words, stopWords);
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
        printAll(sort(countFrequencies(words)));
    }

    // parse function should take a character-level file reader, a list of words (initially empty), and the list of stop words,
    private static void parse(BufferedReader reader, List<String> words, Set<String> stopWords) throws IOException {
        int c = reader.read();
        while (c != -1) {
            // If start of a word
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                String word = getNextWord(reader, "" + (char) c).toLowerCase();
                if (!stopWords.contains(word))
                    words.add(word);
            }
            c = reader.read();
        }
    }

    // Recursively get the next word from reader.
    private static String getNextWord(BufferedReader reader, String word) throws IOException {
        int c = reader.read();
        // Found alphabet
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
            word = word + (char) c;
        } else {
            return word;
        }
        return getNextWord(reader, word);
    }

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
