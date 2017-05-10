package com.av360.service.impl;

import com.av360.service.EvaluationService;
import com.av360.domain.Evaluation;
import com.av360.repository.EvaluationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Evaluation.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService{

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);
    
    private final EvaluationRepository evaluationRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    /**
     * Save a evaluation.
     *
     * @param evaluation the entity to save
     * @return the persisted entity
     */
    @Override
    public Evaluation save(Evaluation evaluation) {
        log.debug("Request to save Evaluation : {}", evaluation);
        Evaluation result = evaluationRepository.save(evaluation);
        return result;
    }

    /**
     *  Get all the evaluations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Evaluation> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluations");
        Page<Evaluation> result = evaluationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one evaluation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Evaluation findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        Evaluation evaluation = evaluationRepository.findOneWithEagerRelationships(id);
        return evaluation;
    }

    /**
     *  Delete the  evaluation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.delete(id);
    }
}
