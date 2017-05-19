package com.av360.service;

import com.av360.domain.EvaluationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing EvaluationTemplate.
 */
public interface EvaluationTemplateService {

    /**
     * Save a evaluationTemplate.
     *
     * @param evaluationTemplate the entity to save
     * @return the persisted entity
     */
    EvaluationTemplate save(EvaluationTemplate evaluationTemplate);

    /**
     *  Get all the evaluationTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EvaluationTemplate> findAll(Pageable pageable);

    Page<EvaluationTemplate> findAllByTeam(Pageable pageable, Long teamId);

    /**
     *  Get the "id" evaluationTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EvaluationTemplate findOne(Long id);

    /**
     *  Delete the "id" evaluationTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
