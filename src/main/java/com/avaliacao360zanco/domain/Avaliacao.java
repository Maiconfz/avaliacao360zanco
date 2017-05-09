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
 * A Avaliacao.
 */
@Entity
@Table(name = "avaliacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Avaliacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "avaliacao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pergunta> perfuntas = new HashSet<>();

    @ManyToOne
    @NotNull
    private User avaliado;

    @ManyToOne
    @NotNull
    private User avaliador;

    @ManyToOne
    @NotNull
    private Equipe equipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Avaliacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Avaliacao descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Pergunta> getPerfuntas() {
        return perfuntas;
    }

    public Avaliacao perfuntas(Set<Pergunta> perguntas) {
        this.perfuntas = perguntas;
        return this;
    }

    public Avaliacao addPerfuntas(Pergunta pergunta) {
        perfuntas.add(pergunta);
        pergunta.setAvaliacao(this);
        return this;
    }

    public Avaliacao removePerfuntas(Pergunta pergunta) {
        perfuntas.remove(pergunta);
        pergunta.setAvaliacao(null);
        return this;
    }

    public void setPerfuntas(Set<Pergunta> perguntas) {
        this.perfuntas = perguntas;
    }

    public User getAvaliado() {
        return avaliado;
    }

    public Avaliacao avaliado(User user) {
        this.avaliado = user;
        return this;
    }

    public void setAvaliado(User user) {
        this.avaliado = user;
    }

    public User getAvaliador() {
        return avaliador;
    }

    public Avaliacao avaliador(User user) {
        this.avaliador = user;
        return this;
    }

    public void setAvaliador(User user) {
        this.avaliador = user;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public Avaliacao equipe(Equipe equipe) {
        this.equipe = equipe;
        return this;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Avaliacao avaliacao = (Avaliacao) o;
        if (avaliacao.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avaliacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
