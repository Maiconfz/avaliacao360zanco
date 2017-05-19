package com.av360.service.impl;

import com.av360.service.EvaluationTemplateService;
import com.av360.domain.EvaluationTemplate;
import com.av360.repository.EvaluationTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing EvaluationTemplate.
 */
@Service
@Transactional
public class EvaluationTemplateServiceImpl implements EvaluationTemplateService {

    private final Logger log = LoggerFactory.getLogger(EvaluationTemplateServiceImpl.class);

    private final EvaluationTemplateRepository evaluationTemplateRepository;

    public EvaluationTemplateServiceImpl(EvaluationTemplateRepository evaluationTemplateRepository) {
        this.evaluationTemplateRepository = evaluationTemplateRepository;
    }

    /**
     * Save a evaluationTemplate.
     *
     * @param evaluationTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public EvaluationTemplate save(EvaluationTemplate evaluationTemplate) {
        log.debug("Request to save EvaluationTemplate : {}", evaluationTemplate);
        EvaluationTemplate result = evaluationTemplateRepository.save(evaluationTemplate);
        return result;
    }

    /**
     *  Get all the evaluationTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluationTemplates");
        Page<EvaluationTemplate> result = evaluationTemplateRepository.findAll(pageable);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationTemplate> findAllByTeam(Pageable pageable, Long teamId) {
        log.debug("Request to get all EvaluationTemplates for team {}", teamId);
        Page<EvaluationTemplate> result = evaluationTemplateRepository.findAllByTeam(pageable, teamId);
        return result;
    }

    /**
     *  Get one evaluationTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EvaluationTemplate findOne(Long id) {
        log.debug("Request to get EvaluationTemplate : {}", id);
        EvaluationTemplate evaluationTemplate = evaluationTemplateRepository.findOne(id);
        return evaluationTemplate;
    }

    /**
     *  Delete the  evaluationTemplate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationTemplate : {}", id);
        evaluationTemplateRepository.delete(id);
    }
}
