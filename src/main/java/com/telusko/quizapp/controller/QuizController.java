package com.telusko.quizapp.controller;

import com.telusko.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestParam("category") String category,
                                             @RequestParam("numQ") int numQ,
                                             @RequestParam("title") String title) {

        return quizService.createQuiz(category, numQ, title);

    }
}
