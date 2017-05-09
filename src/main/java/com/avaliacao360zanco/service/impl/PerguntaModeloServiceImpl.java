package com.avaliacao360zanco.service.impl;

import com.avaliacao360zanco.service.PerguntaModeloService;
import com.avaliacao360zanco.domain.PerguntaModelo;
import com.avaliacao360zanco.repository.PerguntaModeloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing PerguntaModelo.
 */
@Service
@Transactional
public class PerguntaModeloServiceImpl implements PerguntaModeloService{

    private final Logger log = LoggerFactory.getLogger(PerguntaModeloServiceImpl.class);
    
    @Inject
    private PerguntaModeloRepository perguntaModeloRepository;

    /**
     * Save a perguntaModelo.
     *
     * @param perguntaModelo the entity to save
     * @return the persisted entity
     */
    public PerguntaModelo save(PerguntaModelo perguntaModelo) {
        log.debug("Request to save PerguntaModelo : {}", perguntaModelo);
        PerguntaModelo result = perguntaModeloRepository.save(perguntaModelo);
        return result;
    }

    /**
     *  Get all the perguntaModelos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PerguntaModelo> findAll(Pageable pageable) {
        log.debug("Request to get all PerguntaModelos");
        Page<PerguntaModelo> result = perguntaModeloRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one perguntaModelo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PerguntaModelo findOne(Long id) {
        log.debug("Request to get PerguntaModelo : {}", id);
        PerguntaModelo perguntaModelo = perguntaModeloRepository.findOne(id);
        return perguntaModelo;
    }

    /**
     *  Delete the  perguntaModelo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PerguntaModelo : {}", id);
        perguntaModeloRepository.delete(id);
    }
}
