package com.telusko.quizapp.dto;

import com.telusko.quizapp.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {

    private int count;
    private List<Question> questions;
}
