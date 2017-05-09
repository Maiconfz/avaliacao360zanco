package com.avaliacao360zanco.service;

import com.avaliacao360zanco.domain.PerguntaModelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing PerguntaModelo.
 */
public interface PerguntaModeloService {

    /**
     * Save a perguntaModelo.
     *
     * @param perguntaModelo the entity to save
     * @return the persisted entity
     */
    PerguntaModelo save(PerguntaModelo perguntaModelo);

    /**
     *  Get all the perguntaModelos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PerguntaModelo> findAll(Pageable pageable);

    /**
     *  Get the "id" perguntaModelo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PerguntaModelo findOne(Long id);

    /**
     *  Delete the "id" perguntaModelo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
