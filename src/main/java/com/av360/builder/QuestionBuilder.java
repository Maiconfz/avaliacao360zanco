/**
 * 
 */
package com.av360.builder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.av360.domain.Answer;
import com.av360.domain.Evaluation;
import com.av360.domain.QuestionTemplate;

/**
 * @author mzanco
 *
 */
public class QuestionBuilder {

    private Long id;
    private String subject;
    private String text;
    private Set<Answer> answers = new HashSet<>();
    private Evaluation evaluation;

    /**
     * 
     */
    public QuestionBuilder() {
        this.init();
    }

    private void init() {
        this.id = 0L;
        this.subject = "";
        this.text = "";
        this.answers = Collections.emptySet();
        this.evaluation = null; // TODO: Set Empty Evaluation
    }

    public QuestionBuilder createNewQuestion() {
        this.init();
        return this;
    }

    public QuestionBuilder from(QuestionTemplate questionTemplate) {

        return this;
    }

    /**
     * @param id
     *            the id to set
     */
    public QuestionBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public QuestionBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * @param text
     *            the text to set
     */
    public QuestionBuilder setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * @param answers
     *            the answers to set
     */
    public QuestionBuilder setAnswers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    /**
     * @param evaluation
     *            the evaluation to set
     */
    public QuestionBuilder setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
        return this;
    }

}
