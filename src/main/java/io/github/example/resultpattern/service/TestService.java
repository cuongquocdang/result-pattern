package io.github.example.resultpattern.service;

import io.github.example.resultpattern.exception.AppException;
import io.github.example.resultpattern.repository.TestRepository;
import io.github.example.resultpattern.shared.concurrent.CompletableUtils;
import io.github.example.resultpattern.shared.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final AsyncTaskExecutor asyncExecutor;
    private final TestRepository testRepository;

    public Result<String> test(final String error) {
        return switch (error) {
            case "1" -> Result.failure(AppException.ERROR_1);
            case "2" -> Result.failure(AppException.ERROR_2);
            default -> {
                record Result1(String data) {
                }
                record Result2(String data) {
                }
                record Result3(String data) {
                }

                var taskBuilder = CompletableUtils.TaskBuilder.builder(asyncExecutor)
                        .addTask(Result1.class, () -> new Result1(testRepository.test().join()))
                        .addTask(Result2.class, () -> new Result2(testRepository.test().join()))
                        .addTask(Result3.class, () -> new Result3(testRepository.test().join()))
                        .execute();
                var result1 = taskBuilder.getTaskResult(Result1.class);
                var result2 = taskBuilder.getTaskResult(Result2.class);
                var result3 = taskBuilder.getTaskResult(Result3.class);
                yield Result.success(result1.data() + result2.data() + result3.data());
            }
        };
    }
}