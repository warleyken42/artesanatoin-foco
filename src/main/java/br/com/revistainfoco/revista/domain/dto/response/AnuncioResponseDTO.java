package br.com.revistainfoco.revista.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "anuncios", description = "Cadastro de Anuncios")
public class AnuncioResponseDTO implements Serializable {

    @Schema(description = "Id do anuncio", example = "1", required = true)
    private Long id;

    @Schema(description = "Tamanho do anúncio", example = "A4")
    private String tamanho;

    @Schema(description = "Valor do anúncio", example = "160.00")
    private BigDecimal valor;
}
