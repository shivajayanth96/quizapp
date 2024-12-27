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
import com.example.quizapp.request.SubmitRequest;
import com.example.quizapp.wrapper.QuestionWrapper;

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

    public ResponseEntity<List<QuestionWrapper>> getQuestionsByQuizId(Integer id) {

        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Question> questions = quiz.getQuestions();

        return new ResponseEntity<>(convertToQuestionWrapper(questions), HttpStatus.OK);
    }

    private List<QuestionWrapper> convertToQuestionWrapper(List<Question> questions) {
        return questions.stream()
                .map(question -> new QuestionWrapper(question.getId(), question.getOption1(), question.getOption2(),
                        question.getOption3(), question.getOption4()))
                .toList();
    }

    public ResponseEntity<Integer> submitQuiz(Integer id, List<SubmitRequest> request) {

        Quiz quiz = quizRepository.findById(id).orElse(null);

        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Question> questions = quiz.getQuestions();

        int score = 0;

        for (SubmitRequest submitRequest : request) {
            Question question = questions.stream().filter(q -> q.getId().equals(submitRequest.getQuestionId()))
                    .findFirst()
                    .orElse(null);

            if (question == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            if (question.getRightAnswer().equals(submitRequest.getSelectedAnswer())) {
                score++;
            }
        }

        return new ResponseEntity<>(score, HttpStatus.OK);
    }

}
