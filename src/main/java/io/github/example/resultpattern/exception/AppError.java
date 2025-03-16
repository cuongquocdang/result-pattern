package io.github.example.resultpattern.exception;

import io.github.example.resultpattern.shared.result.Result;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppError {

    public static final Result.Error ERROR_1 = new Result.Error("Error.1", "Dummy Error 1");

    public static final Result.Error ERROR_2 = new Result.Error("Error.2", "Dummy Error 2");
}