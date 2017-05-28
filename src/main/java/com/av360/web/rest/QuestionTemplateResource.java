package com.av360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av360.domain.QuestionTemplate;
import com.av360.service.QuestionTemplateService;
import com.av360.web.rest.util.HeaderUtil;
import com.av360.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing QuestionTemplate.
 */
@RestController
@RequestMapping("/api")
public class QuestionTemplateResource {

    private final Logger log = LoggerFactory.getLogger(QuestionTemplateResource.class);

    private static final String ENTITY_NAME = "questionTemplate";

    private final QuestionTemplateService questionTemplateService;

    public QuestionTemplateResource(QuestionTemplateService questionTemplateService) {
        this.questionTemplateService = questionTemplateService;
    }

    /**
     * POST /question-templates : Create a new questionTemplate.
     *
     * @param questionTemplate
     *            the questionTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     *         new questionTemplate, or with status 400 (Bad Request) if the
     *         questionTemplate has already an ID
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PostMapping("/question-templates")
    @Timed
    public ResponseEntity<QuestionTemplate> createQuestionTemplate(@Valid @RequestBody QuestionTemplate questionTemplate) throws URISyntaxException {
        log.debug("REST request to save QuestionTemplate : {}", questionTemplate);
        if (questionTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new questionTemplate cannot already have an ID")).body(null);
        }
        QuestionTemplate result = questionTemplateService.save(questionTemplate);
        return ResponseEntity.created(new URI("/api/question-templates/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /question-templates : Updates an existing questionTemplate.
     *
     * @param questionTemplate
     *            the questionTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         questionTemplate, or with status 400 (Bad Request) if the
     *         questionTemplate is not valid, or with status 500 (Internal
     *         Server Error) if the questionTemplate couldnt be updated
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PutMapping("/question-templates")
    @Timed
    public ResponseEntity<QuestionTemplate> updateQuestionTemplate(@Valid @RequestBody QuestionTemplate questionTemplate) throws URISyntaxException {
        log.debug("REST request to update QuestionTemplate : {}", questionTemplate);
        if (questionTemplate.getId() == null) {
            return createQuestionTemplate(questionTemplate);
        }
        QuestionTemplate result = questionTemplateService.save(questionTemplate);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questionTemplate.getId().toString())).body(result);
    }

    /**
     * GET /question-templates : get all the questionTemplates.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         questionTemplates in body
     */
    @GetMapping("/question-templates")
    @Timed
    public ResponseEntity<List<QuestionTemplate>> getAllQuestionTemplates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of QuestionTemplates");
        Page<QuestionTemplate> page = questionTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/question-templates/evaluation-template/{evaluationTemplateId}")
    @Timed
    public ResponseEntity<List<QuestionTemplate>> getAllQuestionTemplatesByEvaluationTemplate(@ApiParam Pageable pageable, @PathVariable Long evaluationTemplateId) {
        log.debug("REST request to get a page of QuestionTemplates");
        Page<QuestionTemplate> page = questionTemplateService.findAllByEvaluationTemplate(pageable, evaluationTemplateId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/question-templates/evaluation-template/" + evaluationTemplateId);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /question-templates/:id : get the "id" questionTemplate.
     *
     * @param id
     *            the id of the questionTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         questionTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/question-templates/{id}")
    @Timed
    public ResponseEntity<QuestionTemplate> getQuestionTemplate(@PathVariable Long id) {
        log.debug("REST request to get QuestionTemplate : {}", id);
        QuestionTemplate questionTemplate = questionTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(questionTemplate));
    }

    /**
     * DELETE /question-templates/:id : delete the "id" questionTemplate.
     *
     * @param id
     *            the id of the questionTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionTemplate(@PathVariable Long id) {
        log.debug("REST request to delete QuestionTemplate : {}", id);
        questionTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
