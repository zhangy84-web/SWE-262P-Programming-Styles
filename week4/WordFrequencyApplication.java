import java.util.Arrays;
import java.util.List;

public class WordFrequencyApplication {
    private final EventManager eventManager;

    public WordFrequencyApplication(EventManager eventManager) {
        this.eventManager = eventManager;
        this.eventManager.subscribe("run", this::run);
        this.eventManager.subscribe("eof", this::stop);
    }

    public void run(List<Object> event) {
        // Event format: ["run", path_to_file]
        String pathToFile = (String) event.get(1);

        System.out.println(pathToFile);
        
        this.eventManager.publish(Arrays.asList((Object)"load", pathToFile));
        this.eventManager.publish(Arrays.asList((Object)"start", null));
    }

    public void stop(List<Object> event) {
        // Event format: ["eof", null]
        this.eventManager.publish(Arrays.asList((Object)"print", null));
    }
}