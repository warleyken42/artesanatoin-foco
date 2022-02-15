package br.com.revistainfoco.revista.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeRequestDTO implements Serializable {

    @NotNull(message = "O campo nome é obrigatório")
    @Schema(description = "Nome da cidade que se deseja cadastrar", example = "Jaguaribe", required = true)
    private String nome;

    @JsonBackReference
    @Schema(description = "Nome do estado ao qual a cidade pertence", example = "Ceará", required = true)
    private EstadoRequestDTO estado;

    public CidadeRequestDTO() {
    }

    public CidadeRequestDTO(String nome, EstadoRequestDTO estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoRequestDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoRequestDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CidadeRequestDTO that = (CidadeRequestDTO) o;
        return Objects.equals(nome, that.nome) && Objects.equals(estado, that.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, estado);
    }
}
