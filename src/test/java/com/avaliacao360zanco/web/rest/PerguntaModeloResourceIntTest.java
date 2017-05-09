package com.avaliacao360zanco.web.rest;

import com.avaliacao360zanco.Avaliacao360ZancoApp;

import com.avaliacao360zanco.domain.PerguntaModelo;
import com.avaliacao360zanco.domain.AvaliacaoModelo;
import com.avaliacao360zanco.repository.PerguntaModeloRepository;
import com.avaliacao360zanco.service.PerguntaModeloService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PerguntaModeloResource REST controller.
 *
 * @see PerguntaModeloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ZancoApp.class)
public class PerguntaModeloResourceIntTest {

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    @Inject
    private PerguntaModeloRepository perguntaModeloRepository;

    @Inject
    private PerguntaModeloService perguntaModeloService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPerguntaModeloMockMvc;

    private PerguntaModelo perguntaModelo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PerguntaModeloResource perguntaModeloResource = new PerguntaModeloResource();
        ReflectionTestUtils.setField(perguntaModeloResource, "perguntaModeloService", perguntaModeloService);
        this.restPerguntaModeloMockMvc = MockMvcBuilders.standaloneSetup(perguntaModeloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerguntaModelo createEntity(EntityManager em) {
        PerguntaModelo perguntaModelo = new PerguntaModelo()
                .assunto(DEFAULT_ASSUNTO)
                .texto(DEFAULT_TEXTO);
        // Add required entity
        AvaliacaoModelo avaliacaoModelo = AvaliacaoModeloResourceIntTest.createEntity(em);
        em.persist(avaliacaoModelo);
        em.flush();
        perguntaModelo.setAvaliacaoModelo(avaliacaoModelo);
        return perguntaModelo;
    }

    @Before
    public void initTest() {
        perguntaModelo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerguntaModelo() throws Exception {
        int databaseSizeBeforeCreate = perguntaModeloRepository.findAll().size();

        // Create the PerguntaModelo

        restPerguntaModeloMockMvc.perform(post("/api/pergunta-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perguntaModelo)))
            .andExpect(status().isCreated());

        // Validate the PerguntaModelo in the database
        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeCreate + 1);
        PerguntaModelo testPerguntaModelo = perguntaModeloList.get(perguntaModeloList.size() - 1);
        assertThat(testPerguntaModelo.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testPerguntaModelo.getTexto()).isEqualTo(DEFAULT_TEXTO);
    }

    @Test
    @Transactional
    public void createPerguntaModeloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perguntaModeloRepository.findAll().size();

        // Create the PerguntaModelo with an existing ID
        PerguntaModelo existingPerguntaModelo = new PerguntaModelo();
        existingPerguntaModelo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerguntaModeloMockMvc.perform(post("/api/pergunta-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPerguntaModelo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAssuntoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perguntaModeloRepository.findAll().size();
        // set the field null
        perguntaModelo.setAssunto(null);

        // Create the PerguntaModelo, which fails.

        restPerguntaModeloMockMvc.perform(post("/api/pergunta-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perguntaModelo)))
            .andExpect(status().isBadRequest());

        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextoIsRequired() throws Exception {
        int databaseSizeBeforeTest = perguntaModeloRepository.findAll().size();
        // set the field null
        perguntaModelo.setTexto(null);

        // Create the PerguntaModelo, which fails.

        restPerguntaModeloMockMvc.perform(post("/api/pergunta-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perguntaModelo)))
            .andExpect(status().isBadRequest());

        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerguntaModelos() throws Exception {
        // Initialize the database
        perguntaModeloRepository.saveAndFlush(perguntaModelo);

        // Get all the perguntaModeloList
        restPerguntaModeloMockMvc.perform(get("/api/pergunta-modelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perguntaModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())));
    }

    @Test
    @Transactional
    public void getPerguntaModelo() throws Exception {
        // Initialize the database
        perguntaModeloRepository.saveAndFlush(perguntaModelo);

        // Get the perguntaModelo
        restPerguntaModeloMockMvc.perform(get("/api/pergunta-modelos/{id}", perguntaModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perguntaModelo.getId().intValue()))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerguntaModelo() throws Exception {
        // Get the perguntaModelo
        restPerguntaModeloMockMvc.perform(get("/api/pergunta-modelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerguntaModelo() throws Exception {
        // Initialize the database
        perguntaModeloService.save(perguntaModelo);

        int databaseSizeBeforeUpdate = perguntaModeloRepository.findAll().size();

        // Update the perguntaModelo
        PerguntaModelo updatedPerguntaModelo = perguntaModeloRepository.findOne(perguntaModelo.getId());
        updatedPerguntaModelo
                .assunto(UPDATED_ASSUNTO)
                .texto(UPDATED_TEXTO);

        restPerguntaModeloMockMvc.perform(put("/api/pergunta-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerguntaModelo)))
            .andExpect(status().isOk());

        // Validate the PerguntaModelo in the database
        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeUpdate);
        PerguntaModelo testPerguntaModelo = perguntaModeloList.get(perguntaModeloList.size() - 1);
        assertThat(testPerguntaModelo.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testPerguntaModelo.getTexto()).isEqualTo(UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void updateNonExistingPerguntaModelo() throws Exception {
        int databaseSizeBeforeUpdate = perguntaModeloRepository.findAll().size();

        // Create the PerguntaModelo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerguntaModeloMockMvc.perform(put("/api/pergunta-modelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perguntaModelo)))
            .andExpect(status().isCreated());

        // Validate the PerguntaModelo in the database
        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerguntaModelo() throws Exception {
        // Initialize the database
        perguntaModeloService.save(perguntaModelo);

        int databaseSizeBeforeDelete = perguntaModeloRepository.findAll().size();

        // Get the perguntaModelo
        restPerguntaModeloMockMvc.perform(delete("/api/pergunta-modelos/{id}", perguntaModelo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PerguntaModelo> perguntaModeloList = perguntaModeloRepository.findAll();
        assertThat(perguntaModeloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
