package com.avaliacao360zanco.web.rest;

import com.avaliacao360zanco.Avaliacao360ZancoApp;

import com.avaliacao360zanco.domain.AvaliacaoControle;
import com.avaliacao360zanco.domain.AvaliacaoModelo;
import com.avaliacao360zanco.domain.User;
import com.avaliacao360zanco.domain.User;
import com.avaliacao360zanco.repository.AvaliacaoControleRepository;
import com.avaliacao360zanco.service.AvaliacaoControleService;

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
 * Test class for the AvaliacaoControleResource REST controller.
 *
 * @see AvaliacaoControleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ZancoApp.class)
public class AvaliacaoControleResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Inject
    private AvaliacaoControleRepository avaliacaoControleRepository;

    @Inject
    private AvaliacaoControleService avaliacaoControleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAvaliacaoControleMockMvc;

    private AvaliacaoControle avaliacaoControle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvaliacaoControleResource avaliacaoControleResource = new AvaliacaoControleResource();
        ReflectionTestUtils.setField(avaliacaoControleResource, "avaliacaoControleService", avaliacaoControleService);
        this.restAvaliacaoControleMockMvc = MockMvcBuilders.standaloneSetup(avaliacaoControleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AvaliacaoControle createEntity(EntityManager em) {
        AvaliacaoControle avaliacaoControle = new AvaliacaoControle()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        AvaliacaoModelo avaliacaoModelo = AvaliacaoModeloResourceIntTest.createEntity(em);
        em.persist(avaliacaoModelo);
        em.flush();
        avaliacaoControle.setAvaliacaoModelo(avaliacaoModelo);
        // Add required entity
        User avaliado = UserResourceIntTest.createEntity(em);
        em.persist(avaliado);
        em.flush();
        avaliacaoControle.setAvaliado(avaliado);
        // Add required entity
        User avaliador = UserResourceIntTest.createEntity(em);
        em.persist(avaliador);
        em.flush();
        avaliacaoControle.setAvaliador(avaliador);
        return avaliacaoControle;
    }

    @Before
    public void initTest() {
        avaliacaoControle = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvaliacaoControle() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoControleRepository.findAll().size();

        // Create the AvaliacaoControle

        restAvaliacaoControleMockMvc.perform(post("/api/avaliacao-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoControle)))
            .andExpect(status().isCreated());

        // Validate the AvaliacaoControle in the database
        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeCreate + 1);
        AvaliacaoControle testAvaliacaoControle = avaliacaoControleList.get(avaliacaoControleList.size() - 1);
        assertThat(testAvaliacaoControle.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAvaliacaoControle.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAvaliacaoControleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoControleRepository.findAll().size();

        // Create the AvaliacaoControle with an existing ID
        AvaliacaoControle existingAvaliacaoControle = new AvaliacaoControle();
        existingAvaliacaoControle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoControleMockMvc.perform(post("/api/avaliacao-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAvaliacaoControle)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoControleRepository.findAll().size();
        // set the field null
        avaliacaoControle.setNome(null);

        // Create the AvaliacaoControle, which fails.

        restAvaliacaoControleMockMvc.perform(post("/api/avaliacao-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoControle)))
            .andExpect(status().isBadRequest());

        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoControleRepository.findAll().size();
        // set the field null
        avaliacaoControle.setDescricao(null);

        // Create the AvaliacaoControle, which fails.

        restAvaliacaoControleMockMvc.perform(post("/api/avaliacao-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoControle)))
            .andExpect(status().isBadRequest());

        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvaliacaoControles() throws Exception {
        // Initialize the database
        avaliacaoControleRepository.saveAndFlush(avaliacaoControle);

        // Get all the avaliacaoControleList
        restAvaliacaoControleMockMvc.perform(get("/api/avaliacao-controles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacaoControle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getAvaliacaoControle() throws Exception {
        // Initialize the database
        avaliacaoControleRepository.saveAndFlush(avaliacaoControle);

        // Get the avaliacaoControle
        restAvaliacaoControleMockMvc.perform(get("/api/avaliacao-controles/{id}", avaliacaoControle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacaoControle.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvaliacaoControle() throws Exception {
        // Get the avaliacaoControle
        restAvaliacaoControleMockMvc.perform(get("/api/avaliacao-controles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvaliacaoControle() throws Exception {
        // Initialize the database
        avaliacaoControleService.save(avaliacaoControle);

        int databaseSizeBeforeUpdate = avaliacaoControleRepository.findAll().size();

        // Update the avaliacaoControle
        AvaliacaoControle updatedAvaliacaoControle = avaliacaoControleRepository.findOne(avaliacaoControle.getId());
        updatedAvaliacaoControle
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO);

        restAvaliacaoControleMockMvc.perform(put("/api/avaliacao-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvaliacaoControle)))
            .andExpect(status().isOk());

        // Validate the AvaliacaoControle in the database
        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeUpdate);
        AvaliacaoControle testAvaliacaoControle = avaliacaoControleList.get(avaliacaoControleList.size() - 1);
        assertThat(testAvaliacaoControle.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAvaliacaoControle.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAvaliacaoControle() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoControleRepository.findAll().size();

        // Create the AvaliacaoControle

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvaliacaoControleMockMvc.perform(put("/api/avaliacao-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacaoControle)))
            .andExpect(status().isCreated());

        // Validate the AvaliacaoControle in the database
        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvaliacaoControle() throws Exception {
        // Initialize the database
        avaliacaoControleService.save(avaliacaoControle);

        int databaseSizeBeforeDelete = avaliacaoControleRepository.findAll().size();

        // Get the avaliacaoControle
        restAvaliacaoControleMockMvc.perform(delete("/api/avaliacao-controles/{id}", avaliacaoControle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AvaliacaoControle> avaliacaoControleList = avaliacaoControleRepository.findAll();
        assertThat(avaliacaoControleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
