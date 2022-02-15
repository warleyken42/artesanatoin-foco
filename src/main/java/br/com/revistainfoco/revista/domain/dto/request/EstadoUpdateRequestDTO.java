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
public class EstadoUpdateRequestDTO implements Serializable {

    @Schema(description = "ID do estado que se deseja atualizar", example = "1", required = true)
    private Long id;

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Schema(description = "Nome do estado que se deseja atualizar", example = "São Paulo", required = true)
    private String nome;

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Size(min = 2, max = 2, message = "O campo uf deve ter exatamente dois caracteres")
    @Schema(description = "Unidade Federativa do estado que se deseja atualizar", example = "SP", required = true)
    private String uf;

    @JsonManagedReference
    @Schema(description = "Cidades que pertencem ao estado")
    private List<CidadeUpdateRequestDTO> cidades = new ArrayList<>();

    public EstadoUpdateRequestDTO() {
    }

    public EstadoUpdateRequestDTO(Long id, String nome, String uf) {
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

    public List<CidadeUpdateRequestDTO> getCidades() {
        return cidades;
    }

    public void setCidades(List<CidadeUpdateRequestDTO> cidades) {
        this.cidades = cidades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoUpdateRequestDTO that = (EstadoUpdateRequestDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
