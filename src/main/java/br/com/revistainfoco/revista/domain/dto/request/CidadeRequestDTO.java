package br.com.revistainfoco.revista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeRequestDTO implements Serializable {

    @NotNull(message = "O campo nome é obrigatório")
    @Schema(description = "Nome da cidade que se deseja cadastrar", example = "Jaguaribe", required = true)
    private String nome;

    @Schema(description = "Estado que pertence a cidade que se deseja cadastrar", required = true)
    private EstadoRequestDTO estado;
}
