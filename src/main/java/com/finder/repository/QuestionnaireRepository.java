package com.finder.repository;

import com.finder.domain.Questionnaire;
import com.finder.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Optional<List<Questionnaire>> findAllByUser(User user);
}