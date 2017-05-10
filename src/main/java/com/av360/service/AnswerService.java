package com.av360.service;

import com.av360.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Answer.
 */
public interface AnswerService {

    /**
     * Save a answer.
     *
     * @param answer the entity to save
     * @return the persisted entity
     */
    Answer save(Answer answer);

    /**
     *  Get all the answers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Answer> findAll(Pageable pageable);

    /**
     *  Get the "id" answer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Answer findOne(Long id);

    /**
     *  Delete the "id" answer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}