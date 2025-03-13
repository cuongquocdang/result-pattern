package io.github.example.resultpattern.exception;

import io.github.example.resultpattern.shared.result.Error;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AppException {

    public static final Error ERROR_1 = new Error("Error.1", "Dummy Error 1");

    public static final Error ERROR_2 = new Error("Error.2", "Dummy Error 2");
}