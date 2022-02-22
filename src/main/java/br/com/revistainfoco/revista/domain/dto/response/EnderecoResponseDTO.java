package br.com.revistainfoco.revista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.Serializable;
import java.util.Objects;

@Tag(name = "enderecos", description = "Cadastro de Endereços")
public class EnderecoResponseDTO implements Serializable {

    @Schema(description = "ID do endereço que se deseja atualizar", example = "1", required = true)
    private Long id;

    @Schema(description = "Nome do logradouro que se deseja atualizar", example = "Rua Cidade Lion", required = true)
    private String logradouro;

    @Schema(description = "Cidade do endereço")
    private CidadeResponseDTO cidade;

    @Schema(description = "CEP do endereço", example = "7095190")
    private String cep;

    @Schema(description = "Número do endereço", example = "184")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 32")
    private String complemento;

    @Schema(description = "Bairro onde está localizado o endereço", example = "Jardim Anny")
    private String bairro;

    public EnderecoResponseDTO() {
    }

    public EnderecoResponseDTO(Long id, String logradouro, CidadeResponseDTO cidade, String cep, String numero, String complemento, String bairro) {
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

    public CidadeResponseDTO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeResponseDTO cidade) {
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
        EnderecoResponseDTO that = (EnderecoResponseDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
