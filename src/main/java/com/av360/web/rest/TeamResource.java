package com.av360.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.av360.domain.Team;
import com.av360.service.TeamService;
import com.av360.web.rest.util.HeaderUtil;
import com.av360.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Team.
 */
@RestController
@RequestMapping("/api")
public class TeamResource {

    private final Logger log = LoggerFactory.getLogger(TeamResource.class);

    private static final String ENTITY_NAME = "team";

    private final TeamService teamService;

    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * POST /teams : Create a new team.
     *
     * @param team
     *            the team to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     *         new team, or with status 400 (Bad Request) if the team has
     *         already an ID
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PostMapping("/teams")
    @Timed
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) throws URISyntaxException {
        log.debug("REST request to save Team : {}", team);
        if (team.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new team cannot already have an ID")).body(null);
        }
        Team result = teamService.save(team);
        return ResponseEntity.created(new URI("/api/teams/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /teams : Updates an existing team.
     *
     * @param team
     *            the team to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         team, or with status 400 (Bad Request) if the team is not valid,
     *         or with status 500 (Internal Server Error) if the team couldnt be
     *         updated
     * @throws URISyntaxException
     *             if the Location URI syntax is incorrect
     */
    @PutMapping("/teams")
    @Timed
    public ResponseEntity<Team> updateTeam(@Valid @RequestBody Team team) throws URISyntaxException {
        log.debug("REST request to update Team : {}", team);
        if (team.getId() == null) {
            return createTeam(team);
        }
        Team result = teamService.save(team);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, team.getId().toString())).body(result);
    }

    /**
     * GET /teams : get all the teams.
     *
     * @param pageable
     *            the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teams in
     *         body
     */
    @GetMapping("/teams")
    @Timed
    public ResponseEntity<List<Team>> getAllTeams(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Teams");
        Page<Team> page = teamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/teams/leader/{leaderId}")
    @Timed
    public ResponseEntity<List<Team>> getAllTeamsFromLeader(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Teams from current User as Leader");
        Page<Team> page = teamService.findByLeader(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/teams/member/{memberId}")
    @Timed
    public ResponseEntity<List<Team>> getAllTeamsFromMember(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Teams from current User as Member");
        Page<Team> page = teamService.findByMember(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teams");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /teams/:id : get the "id" team.
     *
     * @param id
     *            the id of the team to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the team,
     *         or with status 404 (Not Found)
     */
    @GetMapping("/teams/{id}")
    @Timed
    public ResponseEntity<Team> getTeam(@PathVariable Long id) {
        log.debug("REST request to get Team : {}", id);
        Team team = teamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(team));
    }

    /**
     * DELETE /teams/:id : delete the "id" team.
     *
     * @param id
     *            the id of the team to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teams/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        log.debug("REST request to delete Team : {}", id);
        teamService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
