package br.com.revistainfoco.revista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "estados", description = "Cadastro de Estados")
public class EstadoResponseDTO {

    @Schema(description = "ID do estado", example = "1", required = true)
    private Long id;

    @Schema(description = "Nome do estado", example = "São Paulo", required = true)
    private String nome;

    @Schema(description = "Unidade Federativa do estado", example = "SP", required = true)
    private String uf;
}
