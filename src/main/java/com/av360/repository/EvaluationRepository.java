package com.av360.repository;

import com.av360.domain.Evaluation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Evaluation entity.
 */
@SuppressWarnings("unused")
public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {

    @Query("select evaluation from Evaluation evaluation where evaluation.evaluatedUser.login = ?#{principal.username}")
    List<Evaluation> findByEvaluatedUserIsCurrentUser();

    @Query("select distinct evaluation from Evaluation evaluation left join fetch evaluation.pendingEvaluators")
    List<Evaluation> findAllWithEagerRelationships();

    @Query("select evaluation from Evaluation evaluation left join fetch evaluation.pendingEvaluators where evaluation.id =:id")
    Evaluation findOneWithEagerRelationships(@Param("id") Long id);

}
