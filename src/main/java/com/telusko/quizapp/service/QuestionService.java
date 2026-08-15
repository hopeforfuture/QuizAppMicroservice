package com.telusko.quizapp.service;

import com.telusko.quizapp.dao.QuestionDao;
import com.telusko.quizapp.dto.QuestionRequest;
import com.telusko.quizapp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestions(
            String active,
            String category) {

        // ------------------------------------------------
        // 1. No category provided
        // ------------------------------------------------
        if (category == null || category.isBlank()) {

            // No active parameter
            // => return active questions
            if (active == null || active.isBlank()) {
                return questionDao.findByActive(true);
            }

            // active=all
            if ("all".equalsIgnoreCase(active)) {
                return questionDao.findAll();
            }

            // active=true / false
            Boolean activeStatus = parseActiveStatus(active);

            return questionDao.findByActive(activeStatus);
        }


        // ------------------------------------------------
        // 2. Category provided
        // ------------------------------------------------

        // category + active not provided
        // => active questions in that category
        if (active == null || active.isBlank()) {
            return questionDao.findByCategoryAndActive(
                    category,
                    true
            );
        }

        // category + active=all
        if ("all".equalsIgnoreCase(active)) {
            return questionDao.findByCategory(category);
        }

        // category + active=true/false
        Boolean activeStatus = parseActiveStatus(active);
        
        return questionDao.findByCategoryAndActive(
                category,
                activeStatus
        );
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public Question createQuestion(QuestionRequest request) {

        Question question = new Question();

        question.setQuestionTitle(request.getQuestionTitle());
        question.setOption1(request.getOption1());
        question.setOption2(request.getOption2());
        question.setOption3(request.getOption3());
        question.setOption4(request.getOption4());
        question.setRightAnswer(request.getRightAnswer());
        question.setDifficultylevel(request.getDifficultylevel());
        question.setCategory(request.getCategory());

        return questionDao.save(question);
    }

    public Question updateQuestion(Integer id, QuestionRequest request) {

        // 1. Find existing question
        Question question = questionDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Question not found with id: " + id)
                );

        // 2. Update fields
        question.setQuestionTitle(request.getQuestionTitle());
        question.setOption1(request.getOption1());
        question.setOption2(request.getOption2());
        question.setOption3(request.getOption3());
        question.setOption4(request.getOption4());
        question.setRightAnswer(request.getRightAnswer());
        question.setDifficultylevel(request.getDifficultylevel());
        question.setCategory(request.getCategory());

        // 3. Save updated entity
        return questionDao.save(question);
    }

    // ACTIVATE / DEACTIVATE
    public Question updateQuestionStatus(
            Integer id,
            Boolean active) {

        Question question = questionDao.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Question not found with id: " + id
                        )
                );

        question.setActive(active);

        return questionDao.save(question);
    }

    private Boolean parseActiveStatus(String active) {

        if ("true".equalsIgnoreCase(active)) {
            return true;
        }

        if ("false".equalsIgnoreCase(active)) {
            return false;
        }

        throw new IllegalArgumentException(
                "Invalid active value. Use true, false, or all."
        );
    }
}
