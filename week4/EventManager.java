import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class EventManager {
    private Map<String, List<EventHandler>> subscriptions;

    public EventManager() {
        this.subscriptions = new HashMap<>();
    }

    public void subscribe(String eventType, EventHandler handler) {
        if (!this.subscriptions.containsKey(eventType))
            this.subscriptions.put(eventType, new ArrayList<>());
        this.subscriptions.get(eventType).add(handler);
    }

    public void publish(List<Object> event) {
        String eventType = (String) event.get(0);
        if (this.subscriptions.containsKey(eventType)) {
            for (EventHandler handler : this.subscriptions.get(eventType))
                handler.handle(event);
        }
    }
}