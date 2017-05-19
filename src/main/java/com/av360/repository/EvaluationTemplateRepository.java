package com.av360.repository;

import com.av360.domain.EvaluationTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the EvaluationTemplate entity.
 */
public interface EvaluationTemplateRepository extends JpaRepository<EvaluationTemplate, Long> {

    @Query("select evaluationTemplate from EvaluationTemplate evaluationTemplate where evaluationTemplate.team.id = :teamId")
    Page<EvaluationTemplate> findAllByTeam(Pageable pageable, @Param("teamId") Long teamId);

}
