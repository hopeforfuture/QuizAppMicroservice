package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.QuestionDao;
import com.telusko.quizapp.dao.QuizDao;
import com.telusko.quizapp.dto.QuizResponse;
import com.telusko.quizapp.dto.QuizResultResponse;
import com.telusko.quizapp.model.Question;
import com.telusko.quizapp.model.Quiz;
import com.telusko.quizapp.model.QuizAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuizResponse>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuizResponse> questionsForUser = new ArrayList<>();

        for(Question q : questionsFromDB) {
            QuizResponse qr = new QuizResponse(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qr);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<QuizResultResponse> calculateResult(
            Integer id,
            List<QuizAnswer> quizAnswers) {

        Quiz quiz = quizDao.findById(id).get();
        int right = 0;
        int i = 0;

        List<Question> questions = quiz.getQuestions();

        for (QuizAnswer quizAnswer : quizAnswers) {
            if (quizAnswer.getResponse()
                    .equals(questions.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }

        QuizResultResponse response =
                new QuizResultResponse(
                        "Quiz submitted successfully",
                        right
                );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
