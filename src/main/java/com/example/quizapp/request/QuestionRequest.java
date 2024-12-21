package com.example.quizapp.request;

import lombok.Data;

@Data
public class QuestionRequest {

    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultyLevel;
    private String category;

}
