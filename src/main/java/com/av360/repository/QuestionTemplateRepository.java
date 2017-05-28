package com.av360.repository;

import com.av360.domain.QuestionTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the QuestionTemplate entity.
 */
public interface QuestionTemplateRepository extends JpaRepository<QuestionTemplate, Long> {
    @Query("select questionTemplate from QuestionTemplate questionTemplate where evaluationTemplate.id = :evaluationTemplateId")
    Page<QuestionTemplate> findAllByEvaluationTemplate(Pageable pageable, @Param("evaluationTemplateId") Long evaluationTemplateId);
}
