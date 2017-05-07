package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.Equipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Equipe.
 */
public interface EquipeService {

    /**
     * Save a equipe.
     *
     * @param equipe the entity to save
     * @return the persisted entity
     */
    Equipe save(Equipe equipe);

    /**
     *  Get all the equipes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Equipe> findAll(Pageable pageable);

    /**
     *  Get the "id" equipe.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Equipe findOne(Long id);

    /**
     *  Delete the "id" equipe.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
