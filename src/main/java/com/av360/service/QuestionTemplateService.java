package com.av360.service;

import com.av360.domain.QuestionTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing QuestionTemplate.
 */
public interface QuestionTemplateService {

    /**
     * Save a questionTemplate.
     *
     * @param questionTemplate the entity to save
     * @return the persisted entity
     */
    QuestionTemplate save(QuestionTemplate questionTemplate);

    /**
     *  Get all the questionTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuestionTemplate> findAll(Pageable pageable);

    /**
     *  Get the "id" questionTemplate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuestionTemplate findOne(Long id);

    /**
     *  Delete the "id" questionTemplate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
