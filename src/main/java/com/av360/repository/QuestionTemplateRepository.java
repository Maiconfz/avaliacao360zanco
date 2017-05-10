package com.av360.repository;

import com.av360.domain.QuestionTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionTemplate entity.
 */
@SuppressWarnings("unused")
public interface QuestionTemplateRepository extends JpaRepository<QuestionTemplate,Long> {

}
