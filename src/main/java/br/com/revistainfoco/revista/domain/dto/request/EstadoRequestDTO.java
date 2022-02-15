package br.com.revistainfoco.revista.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "estados", description = "Cadastro de Estados")
public class EstadoRequestDTO implements Serializable {

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Schema(description = "Nome do estado que se deseja cadastrar", example = "São Paulo", required = true)
    private String nome;

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Size(min = 2, max = 2, message = "O campo uf deve ter exatamente dois caracteres")
    @Schema(description = "Unidade Federativa do estado que se deseja cadastrar", example = "SP", required = true)
    private String uf;

    @JsonManagedReference
    @Schema(description = "Cidades que pertencem ao estado")
    private List<CidadeRequestDTO> cidades = new ArrayList<>();

    public EstadoRequestDTO() {
    }

    public EstadoRequestDTO(String nome, String uf) {
        this.nome = nome;
        this.uf = uf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<CidadeRequestDTO> getCidades() {
        return cidades;
    }

    public void setCidades(List<CidadeRequestDTO> cidades) {
        this.cidades = cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoRequestDTO that = (EstadoRequestDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(uf, that.uf) && Objects.equals(cidades, that.cidades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, uf, cidades);
    }
}
