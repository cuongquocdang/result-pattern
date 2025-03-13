package io.github.example.resultpattern.shared.result;

public record Error(String code, String description) {

    public static final Error NONE = new Error("", "");
}