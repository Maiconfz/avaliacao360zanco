package com.avaliacao360zanco.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AvaliacaoControle.
 */
@Entity
@Table(name = "avaliacao_controle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AvaliacaoControle implements Serializable {

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

    @ManyToOne
    @NotNull
    private AvaliacaoModelo avaliacaoModelo;

    @ManyToOne
    @NotNull
    private User avaliado;

    @ManyToOne
    @NotNull
    private User avaliador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public AvaliacaoControle nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public AvaliacaoControle descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public AvaliacaoModelo getAvaliacaoModelo() {
        return avaliacaoModelo;
    }

    public AvaliacaoControle avaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        this.avaliacaoModelo = avaliacaoModelo;
        return this;
    }

    public void setAvaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        this.avaliacaoModelo = avaliacaoModelo;
    }

    public User getAvaliado() {
        return avaliado;
    }

    public AvaliacaoControle avaliado(User user) {
        this.avaliado = user;
        return this;
    }

    public void setAvaliado(User user) {
        this.avaliado = user;
    }

    public User getAvaliador() {
        return avaliador;
    }

    public AvaliacaoControle avaliador(User user) {
        this.avaliador = user;
        return this;
    }

    public void setAvaliador(User user) {
        this.avaliador = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvaliacaoControle avaliacaoControle = (AvaliacaoControle) o;
        if (avaliacaoControle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avaliacaoControle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AvaliacaoControle{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", descricao='" + descricao + "'" +
            '}';
    }
}
