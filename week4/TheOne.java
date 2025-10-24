import java.util.function.Function;

public final class TheOne<T> {
    private final T value;

    private TheOne(T value) {
        this.value = value;
    }

    public static <T> TheOne<T> of(T value) {
        return new TheOne<>(value);
    }

    public <R> TheOne<R> bind(Function<T, R> function) {
        R result = function.apply(this.value);
        return TheOne.of(result);
    }

    public T unwrap() {
        return this.value;
    }
}