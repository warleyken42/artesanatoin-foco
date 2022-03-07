package br.com.revistainfoco.revista.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "anuncio")
public class Anuncio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "formato", length = 2, columnDefinition = "VARCHAR(2)", nullable = false)
    private String tamanho;

    @Column(name = "valor", columnDefinition = "DECIMAL(10,2)", nullable = false, precision = 2, scale = 2)
    private BigDecimal valor;

    public Anuncio() {
    }

    public Anuncio(Long id, String tamanho, BigDecimal valor) {
        this.id = id;
        this.tamanho = tamanho;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anuncio anuncio = (Anuncio) o;
        return Objects.equals(id, anuncio.id) && Objects.equals(tamanho, anuncio.tamanho) && Objects.equals(valor, anuncio.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tamanho, valor);
    }
}
