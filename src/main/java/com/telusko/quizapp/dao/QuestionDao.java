package com.telusko.quizapp.dao;

import com.telusko.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    List<Question> findByActiveTrue();

    List<Question> findByCategoryAndActiveTrue(String category);

    List<Question> findByActive(Boolean active);

    List<Question> findByCategoryAndActive(
            String category,
            Boolean active
    );

    @Query(value = "SELECT * FROM question q WHERE q.category=:category AND active=true ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
