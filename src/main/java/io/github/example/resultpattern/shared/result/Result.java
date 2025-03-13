package io.github.example.resultpattern.shared.result;

import lombok.Getter;

import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public class Result<T> {

    private final boolean isSuccess;

    private final Error error;

    public Result(final boolean isSuccess, final Error error) {
        this.isSuccess = isSuccess;
        this.error = error;
    }

    public T getSuccessData() {
        if (this instanceof Success<T> success) {
            return success.getValue();
        }
        throw new IllegalStateException("Cannot retrieve value from a failure result");
    }

    public static <T, R> R match(final Result<T> result,
                                 final Supplier<R> onSuccess,
                                 final Function<Error, R> onFailure) {
        return result.isSuccess()
                ? onSuccess.get()
                : onFailure.apply(result.getError());
    }

    public static <T> Result<T> success(final T data) {
        return new Success<>(data);
    }

    public static <T> Result<T> failure(final Error error) {
        return new Failure<>(error);
    }

    @Getter
    static class Success<T> extends Result<T> {
        private final T value;

        public Success(T value) {
            super(true, Error.NONE);
            this.value = value;
        }
    }

    static class Failure<T> extends Result<T> {
        public Failure(Error error) {
            super(false, error);
        }
    }
}