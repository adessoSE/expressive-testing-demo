package guidelines.create_testing_dsl.assertions;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Condition<T> {

    private final Predicate<T> predicate;
    private final Supplier<String> description;
    private final Function<T, String> expectation;
    
    public Condition(Predicate<T> predicate, Supplier<String> description, Function<T, String> expectation) {
        this.predicate = predicate;
        this.description = description;
        this.expectation = expectation;
    }

    public boolean eval(T value) {
        return predicate.test(value);
    }
    
    public String description() {
        return description.get();
    }
    
    public String expectation(T value) {
        return expectation.apply(value);
    }
    
}
