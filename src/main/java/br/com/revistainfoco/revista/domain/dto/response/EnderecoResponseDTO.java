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
@Tag(name = "enderecos", description = "Cadastro de Endereços")
public class EnderecoResponseDTO implements Serializable {

    @Schema(description = "ID do endereço cadastrado", example = "1")
    private Long id;

    @Schema(description = "Nome do logradouro do endereço", example = "Rua Cidade Lion")
    private String logradouro;

    @Schema(description = "Cidade onde está localizado o endereço")
    private CidadeResponseDTO cidade;

    @Schema(description = "CEP do endereço", example = "7095190")
    private String cep;

    @Schema(description = "Número do endereço", example = "184")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 32")
    private String complemento;

    @Schema(description = "Bairro onde está localizado o endereço", example = "Jardim Anny")
    private String bairro;
}
