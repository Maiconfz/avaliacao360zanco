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
 * A Evaluation.
 */
@Entity
@Table(name = "evaluation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evaluation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @NotNull
    @Size(max = 200)
    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @OneToMany(mappedBy = "evaluation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private User evaluatedUser;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "evaluation_pending_evaluators",
               joinColumns = @JoinColumn(name="evaluations_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="pending_evaluators_id", referencedColumnName="id"))
    private Set<User> pendingEvaluators = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Evaluation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Evaluation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Evaluation questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Evaluation addQuestions(Question question) {
        this.questions.add(question);
        question.setEvaluation(this);
        return this;
    }

    public Evaluation removeQuestions(Question question) {
        this.questions.remove(question);
        question.setEvaluation(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public User getEvaluatedUser() {
        return evaluatedUser;
    }

    public Evaluation evaluatedUser(User user) {
        this.evaluatedUser = user;
        return this;
    }

    public void setEvaluatedUser(User user) {
        this.evaluatedUser = user;
    }

    public Set<User> getPendingEvaluators() {
        return pendingEvaluators;
    }

    public Evaluation pendingEvaluators(Set<User> users) {
        this.pendingEvaluators = users;
        return this;
    }

    public Evaluation addPendingEvaluators(User user) {
        this.pendingEvaluators.add(user);
        return this;
    }

    public Evaluation removePendingEvaluators(User user) {
        this.pendingEvaluators.remove(user);
        return this;
    }

    public void setPendingEvaluators(Set<User> users) {
        this.pendingEvaluators = users;
    }

    public Team getTeam() {
        return team;
    }

    public Evaluation team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Evaluation evaluation = (Evaluation) o;
        if (evaluation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Evaluation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
