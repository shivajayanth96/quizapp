package com.example.quizapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.quizapp.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    public List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q where q.category = :category ORDER BY RANDOM() LIMIT :numberOfQuestions", nativeQuery = true)
    public List<Question> findRandomQuestionsByCategory(String category, int numberOfQuestions);

}
