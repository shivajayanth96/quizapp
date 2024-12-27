package com.example.quizapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quizapp.request.CreateQuizRequest;
import com.example.quizapp.request.SubmitRequest;
import com.example.quizapp.service.QuizService;
import com.example.quizapp.wrapper.QuestionWrapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createQuiz(@RequestBody CreateQuizRequest entity) {
        return quizService.createQuiz(entity);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuestionByQuizId(@PathVariable Integer id) {
        return quizService.getQuestionsByQuizId(id);
    }

    @PostMapping(path = "/submit/{id}")
    public ResponseEntity<Integer> sumbitQuiz(@PathVariable Integer id, @RequestBody List<SubmitRequest> request) {
        return quizService.submitQuiz(id, request);
    }

}
