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
 * A AvaliacaoModelo.
 */
@Entity
@Table(name = "avaliacao_modelo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AvaliacaoModelo implements Serializable {

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

    @OneToMany(mappedBy = "avaliacaoModelo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PerguntaModelo> perguntasModelos = new HashSet<>();

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

    public AvaliacaoModelo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public AvaliacaoModelo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<PerguntaModelo> getPerguntasModelos() {
        return perguntasModelos;
    }

    public AvaliacaoModelo perguntasModelos(Set<PerguntaModelo> perguntaModelos) {
        this.perguntasModelos = perguntaModelos;
        return this;
    }

    public AvaliacaoModelo addPerguntasModelo(PerguntaModelo perguntaModelo) {
        perguntasModelos.add(perguntaModelo);
        perguntaModelo.setAvaliacaoModelo(this);
        return this;
    }

    public AvaliacaoModelo removePerguntasModelo(PerguntaModelo perguntaModelo) {
        perguntasModelos.remove(perguntaModelo);
        perguntaModelo.setAvaliacaoModelo(null);
        return this;
    }

    public void setPerguntasModelos(Set<PerguntaModelo> perguntaModelos) {
        this.perguntasModelos = perguntaModelos;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public AvaliacaoModelo equipe(Equipe equipe) {
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
        AvaliacaoModelo avaliacaoModelo = (AvaliacaoModelo) o;
        if (avaliacaoModelo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avaliacaoModelo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AvaliacaoModelo{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
