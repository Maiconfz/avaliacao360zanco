package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.Resposta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Resposta.
 */
public interface RespostaService {

    /**
     * Save a resposta.
     *
     * @param resposta the entity to save
     * @return the persisted entity
     */
    Resposta save(Resposta resposta);

    /**
     *  Get all the respostas.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Resposta> findAll(Pageable pageable);

    /**
     *  Get the "id" resposta.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Resposta findOne(Long id);

    /**
     *  Delete the "id" resposta.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
