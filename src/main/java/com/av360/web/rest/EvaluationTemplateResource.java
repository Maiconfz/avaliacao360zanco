package com.av360.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.av360.domain.EvaluationTemplate;
import com.av360.service.EvaluationTemplateService;
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
 * REST controller for managing EvaluationTemplate.
 */
@RestController
@RequestMapping("/api")
public class EvaluationTemplateResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationTemplateResource.class);

    private static final String ENTITY_NAME = "evaluationTemplate";

    private final EvaluationTemplateService evaluationTemplateService;

    public EvaluationTemplateResource(EvaluationTemplateService evaluationTemplateService) {
        this.evaluationTemplateService = evaluationTemplateService;
    }

    /**
     * POST  /evaluation-templates : Create a new evaluationTemplate.
     *
     * @param evaluationTemplate the evaluationTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationTemplate, or with status 400 (Bad Request) if the evaluationTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-templates")
    @Timed
    public ResponseEntity<EvaluationTemplate> createEvaluationTemplate(
            @Valid @RequestBody EvaluationTemplate evaluationTemplate) throws URISyntaxException {
        log.debug("REST request to save EvaluationTemplate : {}", evaluationTemplate);
        if (evaluationTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists",
                    "A new evaluationTemplate cannot already have an ID")).body(null);
        }
        EvaluationTemplate result = evaluationTemplateService.save(evaluationTemplate);
        return ResponseEntity.created(new URI("/api/evaluation-templates/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT  /evaluation-templates : Updates an existing evaluationTemplate.
     *
     * @param evaluationTemplate the evaluationTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationTemplate,
     * or with status 400 (Bad Request) if the evaluationTemplate is not valid,
     * or with status 500 (Internal Server Error) if the evaluationTemplate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-templates")
    @Timed
    public ResponseEntity<EvaluationTemplate> updateEvaluationTemplate(
            @Valid @RequestBody EvaluationTemplate evaluationTemplate) throws URISyntaxException {
        log.debug("REST request to update EvaluationTemplate : {}", evaluationTemplate);
        if (evaluationTemplate.getId() == null) {
            return createEvaluationTemplate(evaluationTemplate);
        }
        EvaluationTemplate result = evaluationTemplateService.save(evaluationTemplate);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluationTemplate.getId().toString()))
                .body(result);
    }

    /**
     * GET  /evaluation-templates : get all the evaluationTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationTemplates in body
     */
    @GetMapping("/evaluation-templates")
    @Timed
    public ResponseEntity<List<EvaluationTemplate>> getAllEvaluationTemplates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EvaluationTemplates");
        Page<EvaluationTemplate> page = evaluationTemplateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluation-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/evaluation-templates/team/{teamId}")
    @Timed
    public ResponseEntity<List<EvaluationTemplate>> getAllEvaluationTemplatesFromTeam(@ApiParam Pageable pageable,
            @PathVariable Long teamId) {
        log.debug("REST request to get a page of EvaluationTemplates from team {}", teamId);
        Page<EvaluationTemplate> page = evaluationTemplateService.findAllByTeam(pageable, teamId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/evaluation-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /evaluation-templates/:id : get the "id" evaluationTemplate.
     *
     * @param id the id of the evaluationTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-templates/{id}")
    @Timed
    public ResponseEntity<EvaluationTemplate> getEvaluationTemplate(@PathVariable Long id) {
        log.debug("REST request to get EvaluationTemplate : {}", id);
        EvaluationTemplate evaluationTemplate = evaluationTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(evaluationTemplate));
    }

    /**
     * DELETE  /evaluation-templates/:id : delete the "id" evaluationTemplate.
     *
     * @param id the id of the evaluationTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteEvaluationTemplate(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationTemplate : {}", id);
        evaluationTemplateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
