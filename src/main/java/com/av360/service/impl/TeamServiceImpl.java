package com.av360.service.impl;

import com.av360.domain.Team;
import com.av360.domain.User;
import com.av360.repository.TeamRepository;
import com.av360.repository.UserRepository;
import com.av360.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing Team.
 */
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a team.
     *
     * @param team the entity to save
     * @return the persisted entity
     */
    @Override
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        Team result = teamRepository.save(team);
        return result;
    }

    /**
     * Get all the teams.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Team> findAll(Pageable pageable) {
        log.debug("Request to get all Teams");
        Page<Team> result = teamRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one team by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Team findOne(Long id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOneWithEagerRelationships(id);
        return team;
    }

    /**
     * Delete the  team by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.delete(id);
    }

    @Override
    public Page<Team> findByLeader(Pageable pageable) {
        log.debug("Request to get all Teams where currentUser is Leader");
        Page<Team> result = teamRepository.findByLeaderIsCurrentUser(pageable);
        return result;
    }

    @Override
    public Page<Team> findByMember(Pageable pageable) {
        log.debug("Request to get all Teams where currentUser is member");
        User user = this.userRepository.findOneIsCurrentUser();
        Page<Team> result = teamRepository.findAllWhereCurrentUserIsMember(pageable, user);
        return result;
    }

}
