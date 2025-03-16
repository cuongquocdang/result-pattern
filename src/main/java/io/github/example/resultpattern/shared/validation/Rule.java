package io.github.example.resultpattern.shared.validation;

import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class Rule<T, V> {

    private final Function<T, V> extractor;
    private Predicate<V> condition;
    private String message;

    public Rule<T, V> notEmpty(final String message) {
        this.condition = value -> value != null && !value.toString().trim().isEmpty();
        this.message = message;
        return this;
    }

    public Rule<T, V> notNull(final String message) {
        this.condition = Objects::nonNull;
        this.message = message;
        return this;
    }

    public Rule<T, V> must(final Predicate<V> condition, final String message) {
        this.condition = condition;
        this.message = message;
        return this;
    }

    public ValidationResult validate(final T instance) {
        V value = extractor.apply(instance);
        if (condition != null && !condition.test(value)) {
            return new ValidationResult(false, message);
        }
        return new ValidationResult(true, null);
    }

    public record ValidationResult(boolean valid, String message) {
    }
}

