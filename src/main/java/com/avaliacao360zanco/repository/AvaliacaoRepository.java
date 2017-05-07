package com.avaliacao360zanco.repository;

import com.avaliacao360zanco.domain.Avaliacao;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Avaliacao entity.
 */
@SuppressWarnings("unused")
public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

    @Query("select avaliacao from Avaliacao avaliacao where avaliacao.usuario.login = ?#{principal.username}")
    List<Avaliacao> findByUsuarioIsCurrentUser();

}
