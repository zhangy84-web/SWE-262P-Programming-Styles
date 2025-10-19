import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
public class Seven {
    public static void main(String[] args) throws IOException {
        Set<String> stopWords = new HashSet<>(Arrays.asList(Files.readString(Paths.get("../stop_words.txt")).split(",")));
        Files.lines(Paths.get(args[0])).flatMap(line -> Arrays.stream(line.replaceAll("[^a-zA-Z]+", " ").toLowerCase().split("\\s+"))).filter(word -> !word.isEmpty() && !stopWords.contains(word)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().filter(entry -> entry.getValue() > 200).sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));}}