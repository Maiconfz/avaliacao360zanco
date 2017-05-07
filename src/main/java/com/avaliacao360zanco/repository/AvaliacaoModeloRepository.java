package com.avaliacao360zanco.repository;

import com.avaliacao360zanco.domain.AvaliacaoModelo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AvaliacaoModelo entity.
 */
@SuppressWarnings("unused")
public interface AvaliacaoModeloRepository extends JpaRepository<AvaliacaoModelo,Long> {

}
