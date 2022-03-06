package br.com.revistainfoco.revista.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "enderecos", description = "Cadastro de Endereços")
public class EnderecoRequestDTO implements Serializable {

    @Schema(description = "Logradouro do endereço", example = "Rua Cidade Lion", required = true)
    private String logradouro;

    @Schema(description = "Cidade do endereço", required = true)
    private CidadeRequestDTO cidade;

    @Schema(description = "CEP do endereço", example = "7095190", required = true)
    private String cep;

    @Schema(description = "Número do endereço", example = "184", required = true)
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 32", required = true)
    private String complemento;

    @Schema(description = "Bairro onde está localizado o endereço", example = "Jardim Anny")
    private String bairro;
}
