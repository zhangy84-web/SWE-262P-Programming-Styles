import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public class WordFrequencyCounter {
    private EventManager eventManager;
    private Map<String, Integer> wordFrequency;

    public WordFrequencyCounter(EventManager eventManager) {
        this.wordFrequency = new HashMap<>();
        this.eventManager = eventManager;
        this.eventManager.subscribe("valid_word", this::incrementCount);
        this.eventManager.subscribe("print", this::printFreqs);
    }

    public void incrementCount(List<Object> event) {
        // Event format: ["valid_word", word]
        String word = (String) event.get(1);

        this.wordFrequency.put(word, this.wordFrequency.getOrDefault(word, 0) + 1);
    }

    public void printFreqs(List<Object> event) {
        List<Map.Entry<String, Integer>> wordFrequencyList = new LinkedList<>(this.wordFrequency.entrySet());
        Collections.sort(wordFrequencyList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                // To sort from largest to smallest.
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });
        int max = Math.min(wordFrequencyList.size(), 25);
        for (int i = 0; i < max; i++) {
            System.out.println(wordFrequencyList.get(i).getKey() + " - " + wordFrequencyList.get(i).getValue());
        }
    }
}