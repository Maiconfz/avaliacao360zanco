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
 * A EvaluationTemplate.
 */
@Entity
@Table(name = "evaluation_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EvaluationTemplate implements Serializable {

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

    @OneToMany(mappedBy = "evaluationTemplate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionTemplate> questionTemplates = new HashSet<>();

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

    public EvaluationTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public EvaluationTemplate description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<QuestionTemplate> getQuestionTemplates() {
        return questionTemplates;
    }

    public EvaluationTemplate questionTemplates(Set<QuestionTemplate> questionTemplates) {
        this.questionTemplates = questionTemplates;
        return this;
    }

    public EvaluationTemplate addQuestionTemplates(QuestionTemplate questionTemplate) {
        this.questionTemplates.add(questionTemplate);
        questionTemplate.setEvaluationTemplate(this);
        return this;
    }

    public EvaluationTemplate removeQuestionTemplates(QuestionTemplate questionTemplate) {
        this.questionTemplates.remove(questionTemplate);
        questionTemplate.setEvaluationTemplate(null);
        return this;
    }

    public void setQuestionTemplates(Set<QuestionTemplate> questionTemplates) {
        this.questionTemplates = questionTemplates;
    }

    public Team getTeam() {
        return team;
    }

    public EvaluationTemplate team(Team team) {
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
        EvaluationTemplate evaluationTemplate = (EvaluationTemplate) o;
        if (evaluationTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), evaluationTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EvaluationTemplate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
