package com.av360.web.rest;

import com.av360.Avaliacao360ZancoApp;

import com.av360.domain.EvaluationTemplate;
import com.av360.domain.Team;
import com.av360.repository.EvaluationTemplateRepository;
import com.av360.service.EvaluationTemplateService;
import com.av360.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EvaluationTemplateResource REST controller.
 *
 * @see EvaluationTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ZancoApp.class)
public class EvaluationTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EvaluationTemplateRepository evaluationTemplateRepository;

    @Autowired
    private EvaluationTemplateService evaluationTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEvaluationTemplateMockMvc;

    private EvaluationTemplate evaluationTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EvaluationTemplateResource evaluationTemplateResource = new EvaluationTemplateResource(evaluationTemplateService);
        this.restEvaluationTemplateMockMvc = MockMvcBuilders.standaloneSetup(evaluationTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluationTemplate createEntity(EntityManager em) {
        EvaluationTemplate evaluationTemplate = new EvaluationTemplate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Team team = TeamResourceIntTest.createEntity(em);
        em.persist(team);
        em.flush();
        evaluationTemplate.setTeam(team);
        return evaluationTemplate;
    }

    @Before
    public void initTest() {
        evaluationTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvaluationTemplate() throws Exception {
        int databaseSizeBeforeCreate = evaluationTemplateRepository.findAll().size();

        // Create the EvaluationTemplate
        restEvaluationTemplateMockMvc.perform(post("/api/evaluation-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationTemplate)))
            .andExpect(status().isCreated());

        // Validate the EvaluationTemplate in the database
        List<EvaluationTemplate> evaluationTemplateList = evaluationTemplateRepository.findAll();
        assertThat(evaluationTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluationTemplate testEvaluationTemplate = evaluationTemplateList.get(evaluationTemplateList.size() - 1);
        assertThat(testEvaluationTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEvaluationTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEvaluationTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = evaluationTemplateRepository.findAll().size();

        // Create the EvaluationTemplate with an existing ID
        evaluationTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluationTemplateMockMvc.perform(post("/api/evaluation-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EvaluationTemplate> evaluationTemplateList = evaluationTemplateRepository.findAll();
        assertThat(evaluationTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = evaluationTemplateRepository.findAll().size();
        // set the field null
        evaluationTemplate.setName(null);

        // Create the EvaluationTemplate, which fails.

        restEvaluationTemplateMockMvc.perform(post("/api/evaluation-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationTemplate)))
            .andExpect(status().isBadRequest());

        List<EvaluationTemplate> evaluationTemplateList = evaluationTemplateRepository.findAll();
        assertThat(evaluationTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvaluationTemplates() throws Exception {
        // Initialize the database
        evaluationTemplateRepository.saveAndFlush(evaluationTemplate);

        // Get all the evaluationTemplateList
        restEvaluationTemplateMockMvc.perform(get("/api/evaluation-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluationTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getEvaluationTemplate() throws Exception {
        // Initialize the database
        evaluationTemplateRepository.saveAndFlush(evaluationTemplate);

        // Get the evaluationTemplate
        restEvaluationTemplateMockMvc.perform(get("/api/evaluation-templates/{id}", evaluationTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(evaluationTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvaluationTemplate() throws Exception {
        // Get the evaluationTemplate
        restEvaluationTemplateMockMvc.perform(get("/api/evaluation-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvaluationTemplate() throws Exception {
        // Initialize the database
        evaluationTemplateService.save(evaluationTemplate);

        int databaseSizeBeforeUpdate = evaluationTemplateRepository.findAll().size();

        // Update the evaluationTemplate
        EvaluationTemplate updatedEvaluationTemplate = evaluationTemplateRepository.findOne(evaluationTemplate.getId());
        updatedEvaluationTemplate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEvaluationTemplateMockMvc.perform(put("/api/evaluation-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvaluationTemplate)))
            .andExpect(status().isOk());

        // Validate the EvaluationTemplate in the database
        List<EvaluationTemplate> evaluationTemplateList = evaluationTemplateRepository.findAll();
        assertThat(evaluationTemplateList).hasSize(databaseSizeBeforeUpdate);
        EvaluationTemplate testEvaluationTemplate = evaluationTemplateList.get(evaluationTemplateList.size() - 1);
        assertThat(testEvaluationTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEvaluationTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEvaluationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = evaluationTemplateRepository.findAll().size();

        // Create the EvaluationTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEvaluationTemplateMockMvc.perform(put("/api/evaluation-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(evaluationTemplate)))
            .andExpect(status().isCreated());

        // Validate the EvaluationTemplate in the database
        List<EvaluationTemplate> evaluationTemplateList = evaluationTemplateRepository.findAll();
        assertThat(evaluationTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEvaluationTemplate() throws Exception {
        // Initialize the database
        evaluationTemplateService.save(evaluationTemplate);

        int databaseSizeBeforeDelete = evaluationTemplateRepository.findAll().size();

        // Get the evaluationTemplate
        restEvaluationTemplateMockMvc.perform(delete("/api/evaluation-templates/{id}", evaluationTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EvaluationTemplate> evaluationTemplateList = evaluationTemplateRepository.findAll();
        assertThat(evaluationTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluationTemplate.class);
        EvaluationTemplate evaluationTemplate1 = new EvaluationTemplate();
        evaluationTemplate1.setId(1L);
        EvaluationTemplate evaluationTemplate2 = new EvaluationTemplate();
        evaluationTemplate2.setId(evaluationTemplate1.getId());
        assertThat(evaluationTemplate1).isEqualTo(evaluationTemplate2);
        evaluationTemplate2.setId(2L);
        assertThat(evaluationTemplate1).isNotEqualTo(evaluationTemplate2);
        evaluationTemplate1.setId(null);
        assertThat(evaluationTemplate1).isNotEqualTo(evaluationTemplate2);
    }
}
