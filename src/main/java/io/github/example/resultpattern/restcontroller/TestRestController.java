package io.github.example.resultpattern.restcontroller;

import io.github.example.resultpattern.service.TestService;
import io.github.example.resultpattern.shared.result.Error;
import io.github.example.resultpattern.shared.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestRestController {

    private final TestService testService;

    @GetMapping("/test")
    public String test(@RequestParam String error) {
        var result = testService.test(error);
        return Result.match(
                result,
                result::getSuccessData,
                Error::code
        );
    }
}