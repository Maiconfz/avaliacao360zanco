package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.Pergunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Pergunta.
 */
public interface PerguntaService {

    /**
     * Save a pergunta.
     *
     * @param pergunta the entity to save
     * @return the persisted entity
     */
    Pergunta save(Pergunta pergunta);

    /**
     *  Get all the perguntas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Pergunta> findAll(Pageable pageable);

    /**
     *  Get the "id" pergunta.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Pergunta findOne(Long id);

    /**
     *  Delete the "id" pergunta.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
