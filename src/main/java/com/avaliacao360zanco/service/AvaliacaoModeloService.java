package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.AvaliacaoModelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing AvaliacaoModelo.
 */
public interface AvaliacaoModeloService {

    /**
     * Save a avaliacaoModelo.
     *
     * @param avaliacaoModelo the entity to save
     * @return the persisted entity
     */
    AvaliacaoModelo save(AvaliacaoModelo avaliacaoModelo);

    /**
     *  Get all the avaliacaoModelos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvaliacaoModelo> findAll(Pageable pageable);

    /**
     *  Get the "id" avaliacaoModelo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvaliacaoModelo findOne(Long id);

    /**
     *  Delete the "id" avaliacaoModelo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
