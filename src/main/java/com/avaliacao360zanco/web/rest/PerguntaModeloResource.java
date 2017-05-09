package com.avaliacao360zanco.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.avaliacao360zanco.domain.PerguntaModelo;
import com.avaliacao360zanco.service.PerguntaModeloService;
import com.avaliacao360zanco.web.rest.util.HeaderUtil;
import com.avaliacao360zanco.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PerguntaModelo.
 */
@RestController
@RequestMapping("/api")
public class PerguntaModeloResource {

    private final Logger log = LoggerFactory.getLogger(PerguntaModeloResource.class);
        
    @Inject
    private PerguntaModeloService perguntaModeloService;

    /**
     * POST  /pergunta-modelos : Create a new perguntaModelo.
     *
     * @param perguntaModelo the perguntaModelo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new perguntaModelo, or with status 400 (Bad Request) if the perguntaModelo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pergunta-modelos")
    @Timed
    public ResponseEntity<PerguntaModelo> createPerguntaModelo(@Valid @RequestBody PerguntaModelo perguntaModelo) throws URISyntaxException {
        log.debug("REST request to save PerguntaModelo : {}", perguntaModelo);
        if (perguntaModelo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("perguntaModelo", "idexists", "A new perguntaModelo cannot already have an ID")).body(null);
        }
        PerguntaModelo result = perguntaModeloService.save(perguntaModelo);
        return ResponseEntity.created(new URI("/api/pergunta-modelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("perguntaModelo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pergunta-modelos : Updates an existing perguntaModelo.
     *
     * @param perguntaModelo the perguntaModelo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated perguntaModelo,
     * or with status 400 (Bad Request) if the perguntaModelo is not valid,
     * or with status 500 (Internal Server Error) if the perguntaModelo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pergunta-modelos")
    @Timed
    public ResponseEntity<PerguntaModelo> updatePerguntaModelo(@Valid @RequestBody PerguntaModelo perguntaModelo) throws URISyntaxException {
        log.debug("REST request to update PerguntaModelo : {}", perguntaModelo);
        if (perguntaModelo.getId() == null) {
            return createPerguntaModelo(perguntaModelo);
        }
        PerguntaModelo result = perguntaModeloService.save(perguntaModelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("perguntaModelo", perguntaModelo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pergunta-modelos : get all the perguntaModelos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of perguntaModelos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pergunta-modelos")
    @Timed
    public ResponseEntity<List<PerguntaModelo>> getAllPerguntaModelos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PerguntaModelos");
        Page<PerguntaModelo> page = perguntaModeloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pergunta-modelos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pergunta-modelos/:id : get the "id" perguntaModelo.
     *
     * @param id the id of the perguntaModelo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the perguntaModelo, or with status 404 (Not Found)
     */
    @GetMapping("/pergunta-modelos/{id}")
    @Timed
    public ResponseEntity<PerguntaModelo> getPerguntaModelo(@PathVariable Long id) {
        log.debug("REST request to get PerguntaModelo : {}", id);
        PerguntaModelo perguntaModelo = perguntaModeloService.findOne(id);
        return Optional.ofNullable(perguntaModelo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pergunta-modelos/:id : delete the "id" perguntaModelo.
     *
     * @param id the id of the perguntaModelo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pergunta-modelos/{id}")
    @Timed
    public ResponseEntity<Void> deletePerguntaModelo(@PathVariable Long id) {
        log.debug("REST request to delete PerguntaModelo : {}", id);
        perguntaModeloService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("perguntaModelo", id.toString())).build();
    }

}
