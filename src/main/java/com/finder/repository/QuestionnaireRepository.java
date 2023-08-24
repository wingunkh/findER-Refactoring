package com.finder.repository;

import com.finder.domain.Questionnaire;
import com.finder.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    Optional<List<Questionnaire>> findAllByUser(User user);

    @Query(value = "select * from questionnaire where id = :id and family_relations = '본인'", nativeQuery = true)
    Optional<Questionnaire> findMyQuestionnaire(Long id);
}