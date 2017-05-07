package com.avaliacao360zanco.web.rest;

import com.avaliacao360zanco.Avaliacao360ZancoApp;

import com.avaliacao360zanco.domain.Avaliacao;
import com.avaliacao360zanco.domain.User;
import com.avaliacao360zanco.repository.AvaliacaoRepository;
import com.avaliacao360zanco.service.AvaliacaoService;

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
 * Test class for the AvaliacaoResource REST controller.
 *
 * @see AvaliacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Avaliacao360ZancoApp.class)
public class AvaliacaoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    @Inject
    private AvaliacaoService avaliacaoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAvaliacaoMockMvc;

    private Avaliacao avaliacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AvaliacaoResource avaliacaoResource = new AvaliacaoResource();
        ReflectionTestUtils.setField(avaliacaoResource, "avaliacaoService", avaliacaoService);
        this.restAvaliacaoMockMvc = MockMvcBuilders.standaloneSetup(avaliacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Avaliacao createEntity(EntityManager em) {
        Avaliacao avaliacao = new Avaliacao()
                .nome(DEFAULT_NOME)
                .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        User usuario = UserResourceIntTest.createEntity(em);
        em.persist(usuario);
        em.flush();
        avaliacao.setUsuario(usuario);
        return avaliacao;
    }

    @Before
    public void initTest() {
        avaliacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createAvaliacao() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();

        // Create the Avaliacao

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isCreated());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testAvaliacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createAvaliacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = avaliacaoRepository.findAll().size();

        // Create the Avaliacao with an existing ID
        Avaliacao existingAvaliacao = new Avaliacao();
        existingAvaliacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAvaliacao)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoRepository.findAll().size();
        // set the field null
        avaliacao.setNome(null);

        // Create the Avaliacao, which fails.

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isBadRequest());

        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = avaliacaoRepository.findAll().size();
        // set the field null
        avaliacao.setDescricao(null);

        // Create the Avaliacao, which fails.

        restAvaliacaoMockMvc.perform(post("/api/avaliacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isBadRequest());

        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAvaliacaos() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get all the avaliacaoList
        restAvaliacaoMockMvc.perform(get("/api/avaliacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(avaliacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoRepository.saveAndFlush(avaliacao);

        // Get the avaliacao
        restAvaliacaoMockMvc.perform(get("/api/avaliacaos/{id}", avaliacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(avaliacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAvaliacao() throws Exception {
        // Get the avaliacao
        restAvaliacaoMockMvc.perform(get("/api/avaliacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoService.save(avaliacao);

        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Update the avaliacao
        Avaliacao updatedAvaliacao = avaliacaoRepository.findOne(avaliacao.getId());
        updatedAvaliacao
                .nome(UPDATED_NOME)
                .descricao(UPDATED_DESCRICAO);

        restAvaliacaoMockMvc.perform(put("/api/avaliacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAvaliacao)))
            .andExpect(status().isOk());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate);
        Avaliacao testAvaliacao = avaliacaoList.get(avaliacaoList.size() - 1);
        assertThat(testAvaliacao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testAvaliacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingAvaliacao() throws Exception {
        int databaseSizeBeforeUpdate = avaliacaoRepository.findAll().size();

        // Create the Avaliacao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAvaliacaoMockMvc.perform(put("/api/avaliacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(avaliacao)))
            .andExpect(status().isCreated());

        // Validate the Avaliacao in the database
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAvaliacao() throws Exception {
        // Initialize the database
        avaliacaoService.save(avaliacao);

        int databaseSizeBeforeDelete = avaliacaoRepository.findAll().size();

        // Get the avaliacao
        restAvaliacaoMockMvc.perform(delete("/api/avaliacaos/{id}", avaliacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Avaliacao> avaliacaoList = avaliacaoRepository.findAll();
        assertThat(avaliacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
