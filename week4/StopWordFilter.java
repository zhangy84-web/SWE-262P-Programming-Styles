import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

public class StopWordFilter {
    private EventManager eventManager;
    private Set<String> stopWords;

    public StopWordFilter(EventManager eventManager) {
        this.stopWords = new HashSet<>();
        this.eventManager = eventManager;
        this.eventManager.subscribe("load", this::load);
        this.eventManager.subscribe("word", this::isStopWord);
    }

    public void load(List<Object> event) {
        Path filePath = Paths.get("../stop_words.txt");
        try {
            String content = Files.readString(filePath);
            String[] words = content.split(",");
            for (String word : words) {
                this.stopWords.add(word.toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void isStopWord(List<Object> event) {
        // Event format: ["word", word]
        String word = (String) event.get(1);
        
        if (!stopWords.contains(word)) {
            // Event format: ["valid_word", word]
            this.eventManager.publish(Arrays.asList((Object)"valid_word", word));
        }
    }
}