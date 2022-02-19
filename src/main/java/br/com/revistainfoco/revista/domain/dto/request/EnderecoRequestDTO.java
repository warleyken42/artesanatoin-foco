package br.com.revistainfoco.revista.domain.dto.request;

import java.io.Serializable;
import java.util.Objects;

public class EnderecoRequestDTO implements Serializable {

    private String logradouro;
    private CidadeRequestDTO cidade;
    private String cep;
    private String numero;
    private String complemento;
    private String bairro;

    public EnderecoRequestDTO() {
    }

    public EnderecoRequestDTO(String logradouro, CidadeRequestDTO cidade, String cep, String numero, String complemento, String bairro) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public CidadeRequestDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeRequestDTO cidade) {
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
        EnderecoRequestDTO that = (EnderecoRequestDTO) o;
        return Objects.equals(logradouro, that.logradouro) && Objects.equals(cidade, that.cidade) && Objects.equals(cep, that.cep) && Objects.equals(numero, that.numero) && Objects.equals(complemento, that.complemento) && Objects.equals(bairro, that.bairro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logradouro, cidade, cep, numero, complemento, bairro);
    }
}
