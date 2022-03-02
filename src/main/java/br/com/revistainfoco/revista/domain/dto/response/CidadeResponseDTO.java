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
@Tag(name = "cidades", description = "Cadastro de Cidades")
public class CidadeResponseDTO implements Serializable {

    @Schema(description = "ID da cidade", example = "1")
    private Long id;

    @Schema(description = "Nome da cidade", example = "Guarulhos")
    private String nome;

    @Schema(description = "Estado em que a cidade se encontra")
    private EstadoResponseDTO estado;
}
