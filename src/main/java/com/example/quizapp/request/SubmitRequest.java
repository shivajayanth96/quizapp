package com.example.quizapp.request;

import lombok.Data;

@Data
public class SubmitRequest {

    private Integer questionId;
    private String selectedAnswer;

}
