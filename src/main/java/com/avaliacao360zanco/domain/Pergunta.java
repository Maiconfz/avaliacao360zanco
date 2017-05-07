package com.avaliacao360zanco.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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

    @ManyToOne
    @NotNull
    private AvaliacaoModelo avaliacaoModelo;

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

    public AvaliacaoModelo getAvaliacaoModelo() {
        return avaliacaoModelo;
    }

    public Pergunta avaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        this.avaliacaoModelo = avaliacaoModelo;
        return this;
    }

    public void setAvaliacaoModelo(AvaliacaoModelo avaliacaoModelo) {
        this.avaliacaoModelo = avaliacaoModelo;
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
