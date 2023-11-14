package ru.niu.itmo.feedback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author amifideles
 */
@RestController
public class FeedbackController {
    @GetMapping("/test")
    public String foo() {
        return "test project";
    }
}
