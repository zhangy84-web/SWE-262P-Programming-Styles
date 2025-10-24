import java.util.List;

@FunctionalInterface
interface EventHandler {
    void handle(List<Object> event);
}