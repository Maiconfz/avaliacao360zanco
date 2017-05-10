package com.av360.service.impl;

import com.av360.service.QuestionTemplateService;
import com.av360.domain.QuestionTemplate;
import com.av360.repository.QuestionTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing QuestionTemplate.
 */
@Service
@Transactional
public class QuestionTemplateServiceImpl implements QuestionTemplateService{

    private final Logger log = LoggerFactory.getLogger(QuestionTemplateServiceImpl.class);
    
    private final QuestionTemplateRepository questionTemplateRepository;

    public QuestionTemplateServiceImpl(QuestionTemplateRepository questionTemplateRepository) {
        this.questionTemplateRepository = questionTemplateRepository;
    }

    /**
     * Save a questionTemplate.
     *
     * @param questionTemplate the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestionTemplate save(QuestionTemplate questionTemplate) {
        log.debug("Request to save QuestionTemplate : {}", questionTemplate);
        QuestionTemplate result = questionTemplateRepository.save(questionTemplate);
        return result;
    }

    /**
     *  Get all the questionTemplates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionTemplate> findAll(Pageable pageable) {
        log.debug("Request to get all QuestionTemplates");
        Page<QuestionTemplate> result = questionTemplateRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one questionTemplate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public QuestionTemplate findOne(Long id) {
        log.debug("Request to get QuestionTemplate : {}", id);
        QuestionTemplate questionTemplate = questionTemplateRepository.findOne(id);
        return questionTemplate;
    }

    /**
     *  Delete the  questionTemplate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionTemplate : {}", id);
        questionTemplateRepository.delete(id);
    }
}
