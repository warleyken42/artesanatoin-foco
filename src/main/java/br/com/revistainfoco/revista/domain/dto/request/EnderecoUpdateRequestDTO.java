package br.com.revistainfoco.revista.domain.dto.request;

import br.com.revistainfoco.revista.domain.dto.response.CidadeResponseDTO;

import java.io.Serializable;
import java.util.Objects;

public class EnderecoUpdateRequestDTO implements Serializable {

    private Long id;
    private String logradouro;
    private CidadeRequestDTO cidade;
    private String cep;
    private String numero;
    private String complemento;
    private String bairro;

    public EnderecoUpdateRequestDTO() {
    }

    public EnderecoUpdateRequestDTO(Long id, String logradouro, CidadeRequestDTO cidade, String cep, String numero, String complemento, String bairro) {
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
        EnderecoUpdateRequestDTO that = (EnderecoUpdateRequestDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
