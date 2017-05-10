package com.av360.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A QuestionTemplate.
 */
@Entity
@Table(name = "question_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 200)
    @Column(name = "subject", length = 200)
    private String subject;

    @NotNull
    @Size(max = 200)
    @Column(name = "text", length = 200, nullable = false)
    private String text;

    @ManyToOne(optional = false)
    @NotNull
    private EvaluationTemplate evaluationTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public QuestionTemplate subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public QuestionTemplate text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EvaluationTemplate getEvaluationTemplate() {
        return evaluationTemplate;
    }

    public QuestionTemplate evaluationTemplate(EvaluationTemplate evaluationTemplate) {
        this.evaluationTemplate = evaluationTemplate;
        return this;
    }

    public void setEvaluationTemplate(EvaluationTemplate evaluationTemplate) {
        this.evaluationTemplate = evaluationTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuestionTemplate questionTemplate = (QuestionTemplate) o;
        if (questionTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionTemplate{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
