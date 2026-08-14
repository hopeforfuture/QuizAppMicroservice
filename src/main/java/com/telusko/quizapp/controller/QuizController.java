package com.telusko.quizapp.controller;

import com.telusko.quizapp.dto.QuizResponse;
import com.telusko.quizapp.dto.QuizResultResponse;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.QuizAnswer;
import com.telusko.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<List<QuizResponse>> getQuizQuestions(@PathVariable("id") Integer id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<QuizResultResponse> submitQuiz(
            @PathVariable Integer id,
            @RequestBody List<QuizAnswer> quizAnswers) {

        return quizService.calculateResult(id, quizAnswers);
    }

}
