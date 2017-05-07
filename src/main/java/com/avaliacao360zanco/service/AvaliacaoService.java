package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Avaliacao.
 */
public interface AvaliacaoService {

    /**
     * Save a avaliacao.
     *
     * @param avaliacao the entity to save
     * @return the persisted entity
     */
    Avaliacao save(Avaliacao avaliacao);

    /**
     *  Get all the avaliacaos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Avaliacao> findAll(Pageable pageable);

    /**
     *  Get the "id" avaliacao.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Avaliacao findOne(Long id);

    /**
     *  Delete the "id" avaliacao.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
