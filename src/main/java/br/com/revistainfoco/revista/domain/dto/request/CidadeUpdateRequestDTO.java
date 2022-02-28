package br.com.revistainfoco.revista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeUpdateRequestDTO implements Serializable {

    @Schema(description = "ID da cidade que se deseja atualizar", example = "1", required = true)
    private Long id;

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Schema(description = "Nome da cidade que se deseja atualizar", example = "Jaguaribe", required = true)
    private String nome;

    @Schema(description = "Estado que pertence a cidade que se deseja atualizar", required = true)
    private EstadoUpdateRequestDTO estado;
}
