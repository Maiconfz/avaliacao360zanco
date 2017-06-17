package com.av360.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.av360.builder.EvaluationBuilder;
import com.av360.domain.Evaluation;
import com.av360.domain.EvaluationTemplate;
import com.av360.domain.User;
import com.av360.repository.EvaluationRepository;
import com.av360.repository.EvaluationTemplateRepository;
import com.av360.service.EvaluationTemplateService;

/**
 * Service Implementation for managing EvaluationTemplate.
 */
@Service
@Transactional
public class EvaluationTemplateServiceImpl implements EvaluationTemplateService {

    private final Logger log = LoggerFactory.getLogger(EvaluationTemplateServiceImpl.class);

    private final EvaluationTemplateRepository evaluationTemplateRepository;
    private final EvaluationRepository evaluationRepository;

    public EvaluationTemplateServiceImpl(EvaluationTemplateRepository evaluationTemplateRepository, EvaluationRepository evaluationRepository) {
        this.evaluationTemplateRepository = evaluationTemplateRepository;
        this.evaluationRepository = evaluationRepository;
    }

    /**
     * Save a evaluationTemplate.
     *
     * @param evaluationTemplate
     *            the entity to save
     * @return the persisted entity
     */
    @Override
    public EvaluationTemplate save(EvaluationTemplate evaluationTemplate) {
        log.debug("Request to save EvaluationTemplate : {}", evaluationTemplate);
        EvaluationTemplate result = evaluationTemplateRepository.save(evaluationTemplate);
        return result;
    }

    /**
     * Get all the evaluationTemplates.
     *
     * @param pageable
     *            the pagination information
     * @return the list of entities
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
     * Get one evaluationTemplate by id.
     *
     * @param id
     *            the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EvaluationTemplate findOne(Long id) {
        log.debug("Request to get EvaluationTemplate : {}", id);
        EvaluationTemplate evaluationTemplate = evaluationTemplateRepository.findOne(id);
        return evaluationTemplate;
    }

    /**
     * Delete the evaluationTemplate by id.
     *
     * @param id
     *            the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationTemplate : {}", id);
        evaluationTemplateRepository.delete(id);
    }

    @Override
    public void submit(Long evaluationTemplateId) {
        log.debug("Request to submit EvaluationTemplate {}", evaluationTemplateId);
        final EvaluationTemplate evaluationTemplate = this.evaluationTemplateRepository.findOne(evaluationTemplateId);
        final EvaluationBuilder evaluationBuilder = new EvaluationBuilder();

        Set<User> teamMembers = evaluationTemplate.getTeam().getMembers();

        for (User evaluatedUser : teamMembers) {
            final Evaluation evaluation = evaluationBuilder.createNewEvaluation().from(evaluationTemplate).setId(null).setEvaluatedUser(evaluatedUser).setPendingEvaluators(teamMembers).build();
            log.debug("Submiting new evaluation with EvaluatedUser equals {}", evaluatedUser.getLogin());
            this.evaluationRepository.save(evaluation);
        }
        log.debug("Evaluation {} successfully submited", evaluationTemplateId);
    }
}
