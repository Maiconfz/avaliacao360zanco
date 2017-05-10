package com.av360.service.impl;

import com.av360.service.AnswerService;
import com.av360.domain.Answer;
import com.av360.repository.AnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Answer.
 */
@Service
@Transactional
public class AnswerServiceImpl implements AnswerService{

    private final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);
    
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * Save a answer.
     *
     * @param answer the entity to save
     * @return the persisted entity
     */
    @Override
    public Answer save(Answer answer) {
        log.debug("Request to save Answer : {}", answer);
        Answer result = answerRepository.save(answer);
        return result;
    }

    /**
     *  Get all the answers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Answer> findAll(Pageable pageable) {
        log.debug("Request to get all Answers");
        Page<Answer> result = answerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one answer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Answer findOne(Long id) {
        log.debug("Request to get Answer : {}", id);
        Answer answer = answerRepository.findOne(id);
        return answer;
    }

    /**
     *  Delete the  answer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Answer : {}", id);
        answerRepository.delete(id);
    }
}
