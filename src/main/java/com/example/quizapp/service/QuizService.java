package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.entity.Question;
import com.example.quizapp.entity.Quiz;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.repository.QuizRepository;
import com.example.quizapp.request.CreateQuizRequest;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(CreateQuizRequest entity) {

        List<Question> randomQuestionsByCategory = questionRepository
                .findRandomQuestionsByCategory(entity.getCategory(), entity.getNumberOfQuestions());

        Quiz quiz = new Quiz();
        quiz.setTitle(entity.getTitle());
        quiz.setQuestions(randomQuestionsByCategory);

        quizRepository.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

}
