package io.github.example.resultpattern.repository;

import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class TestRepository {

    public CompletableFuture<String> test() {
        return CompletableFuture.completedFuture("Test");
    }
}
