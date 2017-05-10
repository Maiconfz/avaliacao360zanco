package com.av360.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Size(max = 200)
    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EvaluationTemplate> envaluationTemplates = new HashSet<>();

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evaluation> evaluations = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private User leader;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "team_members",
               joinColumns = @JoinColumn(name="teams_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="members_id", referencedColumnName="id"))
    private Set<User> members = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Team description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<EvaluationTemplate> getEnvaluationTemplates() {
        return envaluationTemplates;
    }

    public Team envaluationTemplates(Set<EvaluationTemplate> evaluationTemplates) {
        this.envaluationTemplates = evaluationTemplates;
        return this;
    }

    public Team addEnvaluationTemplates(EvaluationTemplate evaluationTemplate) {
        this.envaluationTemplates.add(evaluationTemplate);
        evaluationTemplate.setTeam(this);
        return this;
    }

    public Team removeEnvaluationTemplates(EvaluationTemplate evaluationTemplate) {
        this.envaluationTemplates.remove(evaluationTemplate);
        evaluationTemplate.setTeam(null);
        return this;
    }

    public void setEnvaluationTemplates(Set<EvaluationTemplate> evaluationTemplates) {
        this.envaluationTemplates = evaluationTemplates;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public Team evaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
        return this;
    }

    public Team addEvaluations(Evaluation evaluation) {
        this.evaluations.add(evaluation);
        evaluation.setTeam(this);
        return this;
    }

    public Team removeEvaluations(Evaluation evaluation) {
        this.evaluations.remove(evaluation);
        evaluation.setTeam(null);
        return this;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public User getLeader() {
        return leader;
    }

    public Team leader(User user) {
        this.leader = user;
        return this;
    }

    public void setLeader(User user) {
        this.leader = user;
    }

    public Set<User> getMembers() {
        return members;
    }

    public Team members(Set<User> users) {
        this.members = users;
        return this;
    }

    public Team addMembers(User user) {
        this.members.add(user);
        return this;
    }

    public Team removeMembers(User user) {
        this.members.remove(user);
        return this;
    }

    public void setMembers(Set<User> users) {
        this.members = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
