package br.com.revistainfoco.revista.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", length = 128, columnDefinition = "VARCHAR(128)")
    private String logradouro;

    @ManyToOne()
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    @Column(name = "cep", length = 8, columnDefinition = "VARCHAR(8)")
    private String cep;

    @Column(name = "numero", length = 10, columnDefinition = "VARCHAR(10)")
    private String numero;

    @Column(name = "complemento", length = 25, columnDefinition = "VARCHAR(25)")
    private String complemento;

    @Column(name = "bairro", length = 128, columnDefinition = "VARCHAR(128)")
    private String bairro;

    public Endereco() {
    }

    public Endereco(Long id, String logradouro, Cidade cidade, String cep, String numero, String complemento, String bairro) {
        this.id = id;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return id.equals(endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
