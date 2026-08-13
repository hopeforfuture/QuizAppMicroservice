package com.telusko.quizapp.controller;

import com.telusko.quizapp.dto.QuestionRequest;
import com.telusko.quizapp.dto.QuestionResponse;
import com.telusko.quizapp.dto.QuestionStatusRequest;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /*@GetMapping()
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }*/

    @GetMapping
    public ResponseEntity<QuestionResponse> getQuestions(
            @RequestParam(required = false) String active,
            @RequestParam(required = false) String category) {

        List<Question> questions =
                questionService.getQuestions(active, category);

        QuestionResponse response =
                new QuestionResponse(questions.size(), questions);

        return ResponseEntity.ok(response);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<QuestionResponse> getQuestionsByCategory(@PathVariable("category") String category) {
        List<Question> questions = questionService.getQuestionsByCategory(category);
        QuestionResponse response =
                new QuestionResponse(questions.size(), questions);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<Question> createQuestion(
            @Valid @RequestBody QuestionRequest request) {

        Question question = questionService.createQuestion(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable Integer id,
            @Valid @RequestBody QuestionRequest request) {

        Question question = questionService.updateQuestion(id, request);

        return ResponseEntity.ok(question);
    }

    // ACTIVATE / DEACTIVATE
    @PatchMapping("/{id}/status")
    public ResponseEntity<Question> updateQuestionStatus(
            @PathVariable Integer id,
            @Valid @RequestBody QuestionStatusRequest request) {

        Question question =
                questionService.updateQuestionStatus(
                        id,
                        request.getActive()
                );

        return ResponseEntity.ok(question);
    }

}
