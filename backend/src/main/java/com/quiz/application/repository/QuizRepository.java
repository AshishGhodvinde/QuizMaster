package com.quiz.application.repository;

import com.quiz.application.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreatedBy_Id(Long userId);
    Optional<Quiz> findByTitle(String title);
    boolean existsByTitle(String title);
}