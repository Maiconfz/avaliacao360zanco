package com.avaliacao360zanco.domain;

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
 * A Pergunta.
 */
@Entity
@Table(name = "pergunta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pergunta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "assunto", nullable = false)
    private String assunto;

    @NotNull
    @Column(name = "texto", nullable = false)
    private String texto;

    @OneToMany(mappedBy = "pergunta")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Resposta> respostas = new HashSet<>();

    @ManyToOne
    @NotNull
    private Avaliacao avaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public Pergunta assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public Pergunta texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Set<Resposta> getRespostas() {
        return respostas;
    }

    public Pergunta respostas(Set<Resposta> respostas) {
        this.respostas = respostas;
        return this;
    }

    public Pergunta addRespostas(Resposta resposta) {
        respostas.add(resposta);
        resposta.setPergunta(this);
        return this;
    }

    public Pergunta removeRespostas(Resposta resposta) {
        respostas.remove(resposta);
        resposta.setPergunta(null);
        return this;
    }

    public void setRespostas(Set<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public Pergunta avaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
        return this;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pergunta pergunta = (Pergunta) o;
        if (pergunta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pergunta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pergunta{" +
            "id=" + id +
            ", assunto='" + assunto + "'" +
            ", texto='" + texto + "'" +
            '}';
    }
}
