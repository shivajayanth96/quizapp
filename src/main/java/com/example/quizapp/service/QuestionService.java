package com.example.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizapp.entity.Question;
import com.example.quizapp.repository.QuestionRepository;
import com.example.quizapp.request.QuestionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> allQuestions = questionRepository.findAll();
        return new ResponseEntity<>(allQuestions, HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        List<Question> questionByCategory = questionRepository.findByCategory(category);
        return new ResponseEntity<>(questionByCategory, HttpStatus.OK);
    }

    public ResponseEntity<Question> addQuestion(QuestionRequest questionRequest) {
        try {
            Question question = objectMapper.convertValue(questionRequest, Question.class);
            questionRepository.save(question);
            return new ResponseEntity<>(question, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(e.getMessage());
        }
    }

}
