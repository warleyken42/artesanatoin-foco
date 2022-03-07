package br.com.revistainfoco.revista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "estados", description = "Cadastro de Estados")
public class EstadoResponseDTO implements Serializable {

    @Schema(description = "ID do estado cadastrado", example = "1")
    private Long id;

    @Schema(description = "Nome do estado cadastrado", example = "São Paulo")
    private String nome;

    @Schema(description = "Unidade Federativa do estado cadastrado", example = "SP")
    private String uf;
}
