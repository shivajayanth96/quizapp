package com.example.quizapp.request;

import lombok.Data;

@Data
public class CreateQuizRequest {

    private String category;
    private int numberOfQuestions;
    private String title;

}
