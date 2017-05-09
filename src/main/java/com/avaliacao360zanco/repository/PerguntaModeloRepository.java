package com.avaliacao360zanco.repository;

import com.avaliacao360zanco.domain.PerguntaModelo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PerguntaModelo entity.
 */
@SuppressWarnings("unused")
public interface PerguntaModeloRepository extends JpaRepository<PerguntaModelo,Long> {

}
