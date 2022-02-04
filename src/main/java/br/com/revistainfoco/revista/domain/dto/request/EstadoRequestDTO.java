package br.com.revistainfoco.revista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "estados", description = "Cadastro de Estados")
public class EstadoRequestDTO {
    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Schema(description = "Nome do estado que se deseja cadastrar", example = "São Paulo", required = true)
    private String nome;

    @NotNull(message = "O campo nome é obrigatório")
    @NotEmpty(message = "O campo nome é obrigatório")
    @Size(min = 2, max = 2, message = "O campo uf deve ter exatamente dois caracteres")
    @Schema(description = "Unidade Federativa do estado que se deseja cadastrar", example = "SP", required = true)
    private String uf;
}
