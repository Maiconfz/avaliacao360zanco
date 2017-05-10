package com.av360.repository;

import com.av360.domain.EvaluationTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EvaluationTemplate entity.
 */
@SuppressWarnings("unused")
public interface EvaluationTemplateRepository extends JpaRepository<EvaluationTemplate,Long> {

}
