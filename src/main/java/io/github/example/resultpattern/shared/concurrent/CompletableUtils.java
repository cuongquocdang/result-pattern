package io.github.example.resultpattern.shared.concurrent;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@UtilityClass
public class CompletableUtils {

    public static <T> Map<Class<?>, T> runTasks(final Executor executor,
                                                final Map<Class<?>, Supplier<T>> tasks) {

        var futures = new HashMap<Class<?>, CompletableFuture<T>>();

        // start all tasks
        tasks.forEach((key, value) -> futures.put(key, CompletableFuture.supplyAsync(value, executor)));

        // wait for all tasks to complete
        CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0])).join();

        var results = new HashMap<Class<?>, T>();
        futures.forEach((key, value) -> {
            var result = value.join();
            if (null != result) {
                results.put(key, result);
            }
        });

        return results;
    }

    public static <T, R> List<R> runIterableTasks(final Executor executor,
                                                  final List<T> items,
                                                  final Predicate<T> itemPredicate,
                                                  final Function<T, R> taskFunction) {

        var futures = items.stream()
                .filter(itemPredicate)
                .map(item -> CompletableFuture.supplyAsync(() -> taskFunction.apply(item), executor))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    public static class TaskBuilder {

        private final Map<Class<?>, Supplier<Object>> tasks = new HashMap<>();
        private final Map<Class<?>, Object> results = new HashMap<>();
        private final Executor executor;

        public TaskBuilder(final Executor executor) {
            this.executor = executor;
        }

        public TaskBuilder addTask(final Class<?> clazz, final Supplier<Object> supplier) {
            tasks.put(clazz, supplier);
            return this;
        }

        public TaskBuilder execute() {
            results.putAll(CompletableUtils.runTasks(executor, tasks));
            return this;
        }

        public <T> T getTaskResult(final Class<T> clazz) {
            return clazz.cast(results.get(clazz));
        }

        public static TaskBuilder builder(final Executor executor) {
            return new TaskBuilder(executor);
        }
    }
}
