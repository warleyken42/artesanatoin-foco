package br.com.revistainfoco.revista.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Objects;

@Tag(name = "estados", description = "Cadastro de Estados")
public class EstadoResponseDTO {

    @Schema(description = "ID do estado", example = "1", required = true)
    private Long id;

    @Schema(description = "Nome do estado", example = "São Paulo", required = true)
    private String nome;

    @Schema(description = "Unidade Federativa do estado", example = "SP", required = true)
    private String uf;

    @JsonManagedReference
    private List<CidadeResponseDTO> cidades;

    public EstadoResponseDTO() {
    }

    public EstadoResponseDTO(Long id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<CidadeResponseDTO> getCidades() {
        return cidades;
    }

    public void setCidades(List<CidadeResponseDTO> cidades) {
        this.cidades = cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoResponseDTO that = (EstadoResponseDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
