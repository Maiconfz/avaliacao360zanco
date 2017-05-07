package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.AvaliacaoControle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing AvaliacaoControle.
 */
public interface AvaliacaoControleService {

    /**
     * Save a avaliacaoControle.
     *
     * @param avaliacaoControle the entity to save
     * @return the persisted entity
     */
    AvaliacaoControle save(AvaliacaoControle avaliacaoControle);

    /**
     *  Get all the avaliacaoControles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AvaliacaoControle> findAll(Pageable pageable);

    /**
     *  Get the "id" avaliacaoControle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AvaliacaoControle findOne(Long id);

    /**
     *  Delete the "id" avaliacaoControle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
