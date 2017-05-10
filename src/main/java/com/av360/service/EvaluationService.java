package com.av360.service;

import com.av360.domain.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Evaluation.
 */
public interface EvaluationService {

    /**
     * Save a evaluation.
     *
     * @param evaluation the entity to save
     * @return the persisted entity
     */
    Evaluation save(Evaluation evaluation);

    /**
     *  Get all the evaluations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Evaluation> findAll(Pageable pageable);

    /**
     *  Get the "id" evaluation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Evaluation findOne(Long id);

    /**
     *  Delete the "id" evaluation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
