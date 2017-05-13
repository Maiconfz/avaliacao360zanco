package com.av360.repository;

import com.av360.domain.Team;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Team entity.
 */
@SuppressWarnings("unused")
public interface TeamRepository extends JpaRepository<Team,Long> {

    @Query("select team from Team team where team.leader.login = ?#{principal.username}")
    List<Team> findByLeaderIsCurrentUser();

    @Query("select distinct team from Team team left join fetch team.members")
    List<Team> findAllWithEagerRelationships();

    @Query("select team from Team team left join fetch team.members where team.id =:id")
    Team findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select team from Team team where team.leader.login = ?#{principal.username}")
    Page<Team> findByLeaderIsCurrentUser(Pageable pageable);
}
