package com.av360.web.rest;

import com.av360.Avaliacao360ZancoApp;

import com.av360.domain.QuestionTemplate;
import com.av360.domain.EvaluationTemplate;
import com.av360.repository.QuestionTemplateRepository;
import com.av360.service.QuestionTemplateService;
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
 * Test class for the QuestionTemplateResource REST controller.
 *
 * @see QuestionTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ZancoApp.class)
public class QuestionTemplateResourceIntTest {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private QuestionTemplateRepository questionTemplateRepository;

    @Autowired
    private QuestionTemplateService questionTemplateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionTemplateMockMvc;

    private QuestionTemplate questionTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionTemplateResource questionTemplateResource = new QuestionTemplateResource(questionTemplateService);
        this.restQuestionTemplateMockMvc = MockMvcBuilders.standaloneSetup(questionTemplateResource)
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
    public static QuestionTemplate createEntity(EntityManager em) {
        QuestionTemplate questionTemplate = new QuestionTemplate()
            .subject(DEFAULT_SUBJECT)
            .text(DEFAULT_TEXT);
        // Add required entity
        EvaluationTemplate evaluationTemplate = EvaluationTemplateResourceIntTest.createEntity(em);
        em.persist(evaluationTemplate);
        em.flush();
        questionTemplate.setEvaluationTemplate(evaluationTemplate);
        return questionTemplate;
    }

    @Before
    public void initTest() {
        questionTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionTemplate() throws Exception {
        int databaseSizeBeforeCreate = questionTemplateRepository.findAll().size();

        // Create the QuestionTemplate
        restQuestionTemplateMockMvc.perform(post("/api/question-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionTemplate)))
            .andExpect(status().isCreated());

        // Validate the QuestionTemplate in the database
        List<QuestionTemplate> questionTemplateList = questionTemplateRepository.findAll();
        assertThat(questionTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionTemplate testQuestionTemplate = questionTemplateList.get(questionTemplateList.size() - 1);
        assertThat(testQuestionTemplate.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testQuestionTemplate.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createQuestionTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionTemplateRepository.findAll().size();

        // Create the QuestionTemplate with an existing ID
        questionTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionTemplateMockMvc.perform(post("/api/question-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionTemplate> questionTemplateList = questionTemplateRepository.findAll();
        assertThat(questionTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionTemplateRepository.findAll().size();
        // set the field null
        questionTemplate.setText(null);

        // Create the QuestionTemplate, which fails.

        restQuestionTemplateMockMvc.perform(post("/api/question-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionTemplate)))
            .andExpect(status().isBadRequest());

        List<QuestionTemplate> questionTemplateList = questionTemplateRepository.findAll();
        assertThat(questionTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionTemplates() throws Exception {
        // Initialize the database
        questionTemplateRepository.saveAndFlush(questionTemplate);

        // Get all the questionTemplateList
        restQuestionTemplateMockMvc.perform(get("/api/question-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getQuestionTemplate() throws Exception {
        // Initialize the database
        questionTemplateRepository.saveAndFlush(questionTemplate);

        // Get the questionTemplate
        restQuestionTemplateMockMvc.perform(get("/api/question-templates/{id}", questionTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionTemplate.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionTemplate() throws Exception {
        // Get the questionTemplate
        restQuestionTemplateMockMvc.perform(get("/api/question-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionTemplate() throws Exception {
        // Initialize the database
        questionTemplateService.save(questionTemplate);

        int databaseSizeBeforeUpdate = questionTemplateRepository.findAll().size();

        // Update the questionTemplate
        QuestionTemplate updatedQuestionTemplate = questionTemplateRepository.findOne(questionTemplate.getId());
        updatedQuestionTemplate
            .subject(UPDATED_SUBJECT)
            .text(UPDATED_TEXT);

        restQuestionTemplateMockMvc.perform(put("/api/question-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionTemplate)))
            .andExpect(status().isOk());

        // Validate the QuestionTemplate in the database
        List<QuestionTemplate> questionTemplateList = questionTemplateRepository.findAll();
        assertThat(questionTemplateList).hasSize(databaseSizeBeforeUpdate);
        QuestionTemplate testQuestionTemplate = questionTemplateList.get(questionTemplateList.size() - 1);
        assertThat(testQuestionTemplate.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testQuestionTemplate.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionTemplate() throws Exception {
        int databaseSizeBeforeUpdate = questionTemplateRepository.findAll().size();

        // Create the QuestionTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionTemplateMockMvc.perform(put("/api/question-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionTemplate)))
            .andExpect(status().isCreated());

        // Validate the QuestionTemplate in the database
        List<QuestionTemplate> questionTemplateList = questionTemplateRepository.findAll();
        assertThat(questionTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionTemplate() throws Exception {
        // Initialize the database
        questionTemplateService.save(questionTemplate);

        int databaseSizeBeforeDelete = questionTemplateRepository.findAll().size();

        // Get the questionTemplate
        restQuestionTemplateMockMvc.perform(delete("/api/question-templates/{id}", questionTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionTemplate> questionTemplateList = questionTemplateRepository.findAll();
        assertThat(questionTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionTemplate.class);
        QuestionTemplate questionTemplate1 = new QuestionTemplate();
        questionTemplate1.setId(1L);
        QuestionTemplate questionTemplate2 = new QuestionTemplate();
        questionTemplate2.setId(questionTemplate1.getId());
        assertThat(questionTemplate1).isEqualTo(questionTemplate2);
        questionTemplate2.setId(2L);
        assertThat(questionTemplate1).isNotEqualTo(questionTemplate2);
        questionTemplate1.setId(null);
        assertThat(questionTemplate1).isNotEqualTo(questionTemplate2);
    }
}
