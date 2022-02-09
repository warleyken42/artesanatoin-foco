package br.com.revistainfoco.revista.domain.dto.request;

import br.com.revistainfoco.revista.domain.dto.response.EstadoResponseDTO;
import br.com.revistainfoco.revista.domain.entity.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeRequestDTO {
    @NotNull(message = "O campo nome é obrigatório")
    @Schema(description = "Nome do Cidade que se deseja cadastrar", example = "Jaguaribe", required = true)
    private String nome;
}
