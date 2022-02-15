package br.com.revistainfoco.revista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Objects;

@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeResponseDTO {

    @Schema(description = "ID da cidade", example = "1")
    private Long id;

    @Schema(description = "Nome da cidade", example = "Guarulhos")
    private String nome;

    @JsonBackReference
    private EstadoResponseDTO estado;

    public CidadeResponseDTO() {
    }

    public CidadeResponseDTO(Long id, String nome, EstadoResponseDTO estado) {
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

    public EstadoResponseDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoResponseDTO estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CidadeResponseDTO that = (CidadeResponseDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
