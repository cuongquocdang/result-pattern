package io.github.example.resultpattern.shared.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class Validator<T> {

    private final List<Rule<T, ?>> rules = new ArrayList<>();

    protected <V> Rule<T, V> ruleFor(final Function<T, V> extractor) {
        Rule<T, V> rule = new Rule<>(extractor);
        rules.add(rule);
        return rule;
    }

    public Optional<String> validateFirstError(final T instance) {
        for (Rule<T, ?> rule : rules) {
            var result = rule.validate(instance);
            if (!result.valid()) {
                return Optional.of(result.message());
            }
        }
        return Optional.empty();
    }
}
