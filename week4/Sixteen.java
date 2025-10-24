import java.util.Arrays;

public class Sixteen {
    public static void main(String[] args) {
        EventManager em = new EventManager();

        new DataStorage(em);
        new StopWordFilter(em);
        new WordFrequencyCounter(em);
        new WordFrequencyApplication(em);

        em.publish(Arrays.asList((Object)"run", args[0]));
    }
}