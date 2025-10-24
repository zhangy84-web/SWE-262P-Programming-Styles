import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataStorage {
    private EventManager eventManager;
    private String data;

    public DataStorage(EventManager eventManager) {
        this.data = "";
        this.eventManager = eventManager;
        this.eventManager.subscribe("load", this::load);
        this.eventManager.subscribe("start", this::produceWords);
    }

    public void load(List<Object> event) {
        // Event format: ["load", path_to_file]
        String filename = (String) event.get(1);

        Path filePath = Paths.get(filename);
        try {
            this.data = Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        this.data = this.data.replaceAll("[^a-zA-Z]", " ").toLowerCase();
    }

    public void produceWords(List<Object> event) {
        String[] words = data.split("\\s+");
        for (String word : words) {
            // Event format: ["word", word]
            this.eventManager.publish(Arrays.asList((Object)"word", word));
        }
        // Event format: ["eof", null]
        this.eventManager.publish(Arrays.asList((Object)"eof", null));
    }
}