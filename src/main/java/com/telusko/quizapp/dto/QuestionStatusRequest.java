package com.telusko.quizapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionStatusRequest {

    @NotNull(message = "Active status is required")
    private Boolean active;
}