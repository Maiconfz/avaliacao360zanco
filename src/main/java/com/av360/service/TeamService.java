package com.av360.service;

import com.av360.domain.Team;
import com.av360.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Team.
 */
public interface TeamService {

    /**
     * Save a team.
     *
     * @param team the entity to save
     * @return the persisted entity
     */
    Team save(Team team);

    /**
     *  Get all the teams.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Team> findAll(Pageable pageable);

    /**
     *  Get all the teams from Leader.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Team> findByLeader(Pageable pageable);

    /**
     *  Get the "id" team.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Team findOne(Long id);

    /**
     *  Delete the "id" team.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    Page<Team> findByMember(Pageable pageable);
}
