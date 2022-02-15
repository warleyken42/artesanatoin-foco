package br.com.revistainfoco.revista.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeUpdateRequestDTO implements Serializable {

    @Schema(description = "ID da cidade que se deseja atualizar", example = "1", required = true)
    private Long id;

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Schema(description = "Nome da cidade que se deseja atualizar", example = "Jaguaribe", required = true)
    private String nome;

    @JsonBackReference
    private EstadoUpdateRequestDTO estado;

    public CidadeUpdateRequestDTO() {
    }

    public CidadeUpdateRequestDTO(Long id, String nome, EstadoUpdateRequestDTO estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoUpdateRequestDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoUpdateRequestDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CidadeUpdateRequestDTO that = (CidadeUpdateRequestDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
