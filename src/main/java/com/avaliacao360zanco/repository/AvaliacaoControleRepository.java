package com.avaliacao360zanco.repository;

import com.avaliacao360zanco.domain.AvaliacaoControle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AvaliacaoControle entity.
 */
@SuppressWarnings("unused")
public interface AvaliacaoControleRepository extends JpaRepository<AvaliacaoControle,Long> {

    @Query("select avaliacaoControle from AvaliacaoControle avaliacaoControle where avaliacaoControle.avaliado.login = ?#{principal.username}")
    List<AvaliacaoControle> findByAvaliadoIsCurrentUser();

    @Query("select avaliacaoControle from AvaliacaoControle avaliacaoControle where avaliacaoControle.avaliador.login = ?#{principal.username}")
    List<AvaliacaoControle> findByAvaliadorIsCurrentUser();

}
