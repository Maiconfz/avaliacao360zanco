/**
 *
 */
package com.av360.builder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.av360.domain.Evaluation;
import com.av360.domain.EvaluationTemplate;
import com.av360.domain.Question;
import com.av360.domain.Team;
import com.av360.domain.User;

/**
 * @author mzanco
 *
 */
public class EvaluationBuilder {

    private Long id;
    private String name;
    private String description;
    private Set<Question> questions = new HashSet<>();
    private User evaluatedUser;
    private Set<User> pendingEvaluators = new HashSet<>();
    private Team team;

    /**
     *
     */
    public EvaluationBuilder() {
        this.init();
    }

    private void init() {
        this.id = 0L;
        this.name = "";
        this.description = "";
        this.questions = Collections.emptySet();
        this.evaluatedUser = null; // TODO: Get a empty User for NullSafe
        this.pendingEvaluators = Collections.emptySet();
        this.team = null; // TODO: Get a empty Team for NullSafe
    }

    public EvaluationBuilder createNewEvaluation() {
        this.init();
        return this;
    }

    /**
     * Copy id, name, description and team from evaluationTemplate
     *
     * Since EvaluationTemplate doesn't have Questions, only QuestionTemplate,
     * it will not be copied
     *
     * @param evaluationTemplate
     * @return
     */
    public EvaluationBuilder from(EvaluationTemplate evaluationTemplate) {
        return this.setId(evaluationTemplate.getId())
                   .setName(evaluationTemplate.getName())
                   .setDescription(evaluationTemplate.getDescription())
                   .setTeam(evaluationTemplate.getTeam());
    }

    /**
     * @param id
     *            the id to set
     */
    public EvaluationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @param name
     *            the name to set
     */
    public EvaluationBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param description
     *            the description to set
     */
    public EvaluationBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * @param questions
     *            the questions to set
     */
    public EvaluationBuilder setQuestions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    /**
     * @param evaluatedUser
     *            the evaluatedUser to set
     */
    public EvaluationBuilder setEvaluatedUser(User evaluatedUser) {
        this.evaluatedUser = evaluatedUser;
        return this;
    }

    /**
     * @param pendingEvaluators
     *            the pendingEvaluators to set
     */
    public EvaluationBuilder setPendingEvaluators(Set<User> pendingEvaluators) {
        this.pendingEvaluators = new HashSet<User>(pendingEvaluators);
        return this;
    }

    /**
     * @param team
     *            the team to set
     */
    public EvaluationBuilder setTeam(Team team) {
        this.team = team;
        return this;
    }

    public Evaluation build() {
        final Evaluation evaluation = new Evaluation();

        evaluation.setId(this.id);
        evaluation.setName(this.name);
        evaluation.setDescription(this.description);
        evaluation.setQuestions(this.questions);
        evaluation.setEvaluatedUser(this.evaluatedUser);
        evaluation.setPendingEvaluators(this.pendingEvaluators);
        evaluation.setTeam(this.team);
        return evaluation;
    }

}
