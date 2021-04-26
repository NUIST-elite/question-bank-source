package edu.nuist.qdb.entity.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionReps extends JpaRepository<Question, Long> {
    Question save(Question question);

    Question findById(long id);

    List<Question> findAll();
}
